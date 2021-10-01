package isv.sap.payment.addon.order.converters.populator;

import java.util.Optional;

import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;

public class BillingOrderPopulator extends OrderPopulator
{
    @Override
    public void populate(final OrderModel source, final OrderData target)
    {
        callSuperPopulate(source, target);

        addBillingAddress(source, target);
    }

    private void addBillingAddress(final AbstractOrderModel source, final AbstractOrderData target)
    {
        Optional.ofNullable(source.getPaymentAddress())
                .ifPresent(address -> target.setBillingAddress(getAddressConverter().convert(address)));
    }

    protected void callSuperPopulate(final OrderModel source, final OrderData target)
    {
        super.populate(source, target);
    }

    @Override
    protected void addPaymentInformation(final AbstractOrderModel source, final AbstractOrderData prototype)
    {
        final PaymentInfoModel paymentInfoModel = source.getPaymentInfo();
        if (paymentInfoModel != null && paymentInfoModel.getBillingAddress() != null)
        {
            final CCPaymentInfoData paymentInfo = new CCPaymentInfoData();
            paymentInfo.setBillingAddress(getAddressConverter().convert(paymentInfoModel.getBillingAddress()));
            prototype.setPaymentInfo(paymentInfo);
        }
    }
}
