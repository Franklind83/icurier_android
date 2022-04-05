package com.dev.todos.Network;

import com.dev.todos.Model.AddSupport.AddSupportRequest;
import com.dev.todos.Model.ApiPayPhone.PaymentPayPhoneRequest;
import com.dev.todos.Model.ApiPayPhone.PaymentPayPhoneResponse;
import com.dev.todos.Model.DeletedUser.DeletedUserRequest;
import com.dev.todos.Model.DeletedUser.DeletedUserResponse;
import com.dev.todos.Model.DeliveryInfo.DeliveryInfoRequest;
import com.dev.todos.Model.DeliveryInfo.DeliveryInfoResponse;
import com.dev.todos.Model.DeliveryStatus.DeliveryStatusRequest;
import com.dev.todos.Model.DeliveryStatus.DeliveryStatusResponse;
import com.dev.todos.Model.Description.DescResponse;
import com.dev.todos.Model.Filter.FilterRequest;
import com.dev.todos.Model.Filter.FilterResponse;
import com.dev.todos.Model.FilterShopper.FilterShopperRequest;
import com.dev.todos.Model.FilterShopper.FilterShopperResponse;
import com.dev.todos.Model.GetAdminBankAccount.AdminBankRequest;
import com.dev.todos.Model.GetAdminBankAccount.AdminBankResponse;
import com.dev.todos.Model.GetBankInfo.GetBankInfoResponse;
import com.dev.todos.Model.GetOrderList.OrderListRequest;
import com.dev.todos.Model.GetOrderList.OrderListResponse;
import com.dev.todos.Model.GetPaymentMethod.GetPaymentMethodResponse;
import com.dev.todos.Model.GetProfileData.GetProfileRequest;
import com.dev.todos.Model.GetProfileData.GetProfileResponse;
import com.dev.todos.Model.GetPurchaseData.GetPurchaseRequest;
import com.dev.todos.Model.GetPurchaseData.GetPurchaseResponse;
import com.dev.todos.Model.GetRating.GetRatingRequest;
import com.dev.todos.Model.GetRating.GetRatingResponse;
import com.dev.todos.Model.GetSupport.SupportRequest;
import com.dev.todos.Model.GetSupport.SupportResponse;
import com.dev.todos.Model.GetTravellerList.TravellerListRequest;
import com.dev.todos.Model.GetTravellerList.TravellerListResponse;
import com.dev.todos.Model.Language.LangRequest;
import com.dev.todos.Model.Language.LangResponse;
import com.dev.todos.Model.Login.LoginRequest;
import com.dev.todos.Model.Login.LoginResponse;
import com.dev.todos.Model.Logout.LogoutRequest;
import com.dev.todos.Model.Logout.LogoutResponse;
import com.dev.todos.Model.Message.MessageResponse;
import com.dev.todos.Model.MessageList.ChatResponse;
import com.dev.todos.Model.MyOrderResponce;
import com.dev.todos.Model.OrderInfomation.OrderInfoRequest;
import com.dev.todos.Model.OrderInfomation.OrderInfoResponse;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Model.Payment.PaymentRequest;
import com.dev.todos.Model.Payment.PaymentResponse;
import com.dev.todos.Model.PaymentBanktransfer.BankTransferRequest;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SaveAmountPayPhone.PayPhoneRequest;
import com.dev.todos.Model.SaveAmountPayPhone.PayPhoneResponse;
import com.dev.todos.Model.SaveProfileData.SaveProfileRequest;
import com.dev.todos.Model.SaveProfileData.SaveProfileResponse;
import com.dev.todos.Model.Signup.SignupRequest;
import com.dev.todos.Model.Signup.SignupResponse;
import com.dev.todos.Model.SingleOrderListResponce;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
import com.dev.todos.Model.SocailLogin.SocailLoginResponse;
import com.dev.todos.Model.StatusInformation.StatusResponse;
import com.dev.todos.Model.TodosCommission.ComRequest;
import com.dev.todos.Model.TodosCommission.ComResponse;
import com.dev.todos.Model.TravellerWallet.TravellerWalletRequest;
import com.dev.todos.Model.TravellerWallet.TravellerWalletResponse;
import com.dev.todos.Model.Trip.TripRequest;
import com.dev.todos.Model.WalletCommission.WalletRequest;
import com.dev.todos.Model.WalletCommission.WalletResponse;
import com.dev.todos.Url.WebService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @POST(WebService.LOGIN)
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST(WebService.SIGNUP)
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);

    @POST(WebService.TRAVELLER_LIST)
    Call<TravellerListResponse> travellerlist(@Body TravellerListRequest travellerListRequest);

    @POST(WebService.ORDER_LIST)
    Call<OrderListResponse> orderlist(@Body OrderListRequest orderListRequest);

    @POST(WebService.LOGOUT)
    Call<LogoutResponse> logout(@Body LogoutRequest logoutRequest);

    @POST(WebService.GET_PROFILE)
    Call<GetProfileResponse> profile(@Body GetProfileRequest getProfileRequest);

    @POST(WebService.UPDATE_PROFILE)
    Call<SaveProfileResponse> saveprofile(@Body SaveProfileRequest saveProfileRequest);

    @POST(WebService.RATING)
    Call<GetRatingResponse> rating(@Body GetRatingRequest getRatingRequest);

    @POST(WebService.ADD_NEW_ORDER_SHOPPER)
    Call<Responce> addOrderWed(@Body JsonObject jsonObject);

    @POST(WebService.UPDATE_ORDER_SHOPPER)
    Call<Responce> updateOrder(@Body JsonObject jsonObject);

    @POST(WebService.GET_ORDER_LIST)
    Call<OrderListResponse> getOrderList(@Body JsonObject jsonObject);

    @POST(WebService.GET_SINGLE_ORDER_DETAIL)
    Call<SingleOrderListResponce> getSingleOrderDetail(@Body JsonObject jsonObject);

    @POST(WebService.GET_SINGLE_DETAIL)
    Call<SingleOrderResponse> getSingleDetail(@Body JsonObject jsonObject);

    @POST(WebService.REMOVE_ORDER)
    Call<Responce> removeProduct(@Body JsonObject jsonObject);

    @POST(WebService.DELETE_ORDER)
    Call<Responce> deletOrder(@Body JsonObject jsonObject);

    @POST(WebService.CANCEL_ORDER)
    Call<Responce> cancelOrder(@Body JsonObject jsonObject);

    @POST(WebService.GET_MY_TRIP)
    Call<Responce> getMyTrip(@Body JsonObject jsonObject);

    @POST(WebService.EDIT_TRIP)
    Call<Responce> editTrip(@Body TripRequest tripRequest);

    @POST(WebService.EDIT_TRIP)
    Call<Responce> editTripObject(@Body JsonObject jsonObject);

    @POST(WebService.CANCEL_TRIP)
    Call<Responce> cancelTrip(@Body JsonObject jsonObject);

    @POST(WebService.ADD_TRIP)
    Call<Responce> addTrip(@Body JsonObject jsonObject);

    @POST(WebService.GET_TRIP_ORDER_LIST)
    Call<MyOrderResponce> getMyOffer(@Body JsonObject jsonObject);

    @POST(WebService.MakeOffer)
    Call<Responce> makeOffer(@Body JsonObject jsonObject);

    @POST(WebService.Filter)
    Call<FilterResponse> filter(@Body FilterRequest filterRequest);

    @POST(WebService.FilterShopper)
    Call<FilterShopperResponse> filtershopper(@Body FilterShopperRequest filterRequest);

    @POST(WebService.Support)
    Call<SupportResponse>support(@Body SupportRequest supportRequest);

    @POST(WebService.ADDSupport)
    Call<Responce> addSupport(@Body AddSupportRequest addSupportRequest);

    @POST(WebService.change_password_web)
    Call<Responce> changePass(@Body JsonObject jsonObject);

    @POST(WebService.forgot_password_web)
    Call<Responce> forgetPass(@Body JsonObject jsonObject);

    @POST(WebService.hire_order_existing_order)
    Call<Responce> hireorder(@Body JsonObject jsonObject);


    @POST(WebService.message_list)
    Call<ChatResponse> getRecentChat(@Body JsonObject jsonObject);

    @POST(WebService.get_message_list)
    Call<MessageResponse> getMessage(@Body JsonObject jsonObject);


    @POST(WebService.add_chat)
    Call<Responce> addChat(@Body JsonObject jsonObject);

    @POST(WebService.accept_order_offer_web)
    Call<Responce> acceptOrder(@Body JsonObject jsonObject);

    @POST(WebService.payment_web)
    Call<PaymentResponse> payment(@Body PaymentRequest paymentRequest);

    @POST(WebService.commission_todos)
    Call<ComResponse> comission(@Body ComRequest comRequest);

    @POST(WebService.Edit_Offer)
    Call<Responce> editoffer(@Body JsonObject jsonObject);

    @POST(WebService.get_purchase_data)
    Call<GetPurchaseResponse> getpurchase(@Body GetPurchaseRequest getPurchaseRequest);

    @POST(WebService.get_order_information_status)
    Call<OrderStatusResponse> getOrderStatus(@Body OrderStatusRequest orderStatusRequest);

    @POST(WebService.get_delivery_info_web)
    Call<DeliveryInfoResponse> getDelivery(@Body DeliveryInfoRequest deliveryInfoRequest);


    @POST(WebService.add_delivery_info_web)
    Call<Responce> addDelivery(@Body JsonObject jsonObject);

    @POST(WebService.add_rating_web)
    Call<Responce> addRating(@Body JsonObject jsonObject);

    @POST(WebService.add_purchase_info_web)
    Call<Responce> addpurshase(@Body JsonObject jsonObject);

    @POST(WebService.add_delivered_order_status_web)
    Call<DeliveryStatusResponse> deliverystatus(@Body DeliveryStatusRequest deliveryStatusRequest);

    @POST(WebService.get_order_information_status)
    Call<OrderInfoResponse> orderinfo(@Body OrderInfoRequest orderInfoRequest);

    @POST(WebService.get_satisfied_status_information)
    Call<StatusResponse> getstatus(@Body JsonObject jsonObject);


    //
    @GET(WebService.get_payment_method_status_web)
    Call<GetPaymentMethodResponse> getpaymentmethod();

    @GET(WebService.get_bank_account_info_list_web)
    Call<GetBankInfoResponse> getbankInfo();

    @POST(WebService.get_admin_bank_account_info_web)
    Call<AdminBankResponse> getAdminBank(@Body AdminBankRequest adminBankRequest);

    @POST(WebService.save_amount_to_pay_for_payphone_web)
    Call<PayPhoneResponse> savePayPhone(@Body PayPhoneRequest payPhoneRequest);

    @POST(WebService.payment_api_after_pay_phone_order_web)
    Call<PaymentPayPhoneResponse> payment(@Body PaymentPayPhoneRequest paymentPayPhoneRequest);

    @POST(WebService.payment_api_bank_transfer_web)
    Call<Responce> banktransfer(@Body BankTransferRequest bankTransferRequest);

    @POST(WebService.wallet_commission_earned_web)
    Call<WalletResponse> wallet(@Body WalletRequest walletRequest);

    @POST(WebService.wallet_traveller_amount_web)
    Call<TravellerWalletResponse> Travellerwallet(@Body TravellerWalletRequest travellerWalletRequest);

    @POST(WebService.social_login)
    Call<SocailLoginResponse> socialLogin(@Body JsonObject jsonObject);

    @POST(WebService.DeletedUser)
    Call<DeletedUserResponse> deleteduser(@Body DeletedUserRequest deletedUserRequest);

    @GET(WebService.desc)
    Call<DescResponse> desc();

    @POST(WebService.LanguageUpdated)
    Call<LangResponse> language(@Body LangRequest langRequest);
}

