package com.dev.todos.Url;

public class WebService {

    //development http://45.79.19.12:8080/
    // production https://icurier.vip/
//    public static final String BASE_URL = "http://45.79.19.12:8080/";
    public static final String BASE_URL = "http://45.79.19.12:8050/";
//    public static final String BASE_URL = "https://icurier.vip/";

    public static final String SIGNUP = BASE_URL + "sign_up_web/";
    public static final String LOGIN = BASE_URL + "login_web/";
    public static final String TRAVELLER_LIST = BASE_URL + "get_traveller_trip_list_web/";
    public static final String ORDER_LIST = BASE_URL + "get_order_list_web/";
    public static final String LOGOUT = BASE_URL + "logout_web/";
    public static final String RATING = BASE_URL + "get_rating_web/";
    public static final String GET_PROFILE = BASE_URL + "get_user_profile_web/";
    public static final String UPDATE_PROFILE = BASE_URL + "update_profile_web/";
//git clone https://TSS_Apps@bitbucket.org/shekhartech/todosandroid.git
    //shopper
    public static final String ADD_NEW_ORDER_SHOPPER= BASE_URL + "add_order_web/";
    public static final String UPDATE_ORDER_SHOPPER= BASE_URL + "update_OrderProduct_Web/";
    public static final String GET_ORDER_LIST= BASE_URL + "get_order_list_web/";
    public static final String GET_SINGLE_ORDER_DETAIL= BASE_URL + "get_single_order_details_web/";
    public static final String REMOVE_ORDER= BASE_URL + "remove_order_product/";
    public static final String DELETE_ORDER= BASE_URL + "delete_order_web/";
    public static final String CANCEL_ORDER= BASE_URL + "cancel_order_web/";
    public static final String GET_MY_TRIP= BASE_URL + "get_my_trip_web/";
    public static final String EDIT_TRIP= BASE_URL + "edit_trip_web/";
    public static final String CANCEL_TRIP= BASE_URL + "cancel_trip_web/";
    public static final String ADD_TRIP= BASE_URL + "add_trip_web/";
    public static final String GET_TRIP_ORDER_LIST= BASE_URL + "get_trip_order_list_web/";

    //trip
    public static final String EditTrip = BASE_URL + "edit_trip_web/";
    public static final String GET_SINGLE_DETAIL= BASE_URL + "get_single_order_details_web/";
    public static final String MakeOffer = BASE_URL + "add_order_offer_web/";

    //filter
//    public static final String Filter = BASE_URL + "filter_order_web/";
    public static final String Filter = BASE_URL + "filter_order_with_latlng_web/";
//    public static final String FilterShopper = BASE_URL + "filter_trip_web/";
    public static final String FilterShopper = BASE_URL + "filter_trip_with_latlng_web/";
    public static final String Support = BASE_URL + "get_support_mail_list_by_user_web/";
    public static final String ADDSupport = BASE_URL +"send_support_mail_to_admin_web/";
    public static final String change_password_web="change_password_web/";
    public static final String forgot_password_web="forgot_password_web/";
    public static final String hire_order_existing_order = "make_hire_order_to_exists_order_web/";
    public static final String message_list = "recent_chat_web/";
    public static final String get_message_list = "get_chat_web/";
    public static final String add_chat = "add_chat_web/";
    public static final String accept_order_offer_web="accept_order_offer_web/";
    public static final String payment_web="payment_API_after_order_web/";
    public static final String commission_todos="get_icurier_commission_by_amount_web/";
    public static final String Edit_Offer="edit_order_offer_web/";

    //traveller offer
    public static final String get_purchase_data="get_purchase_info_web/";
    public static final String get_delivery_info_web="get_delivery_info_web/";
    public static final String add_delivery_info_web="add_delivery_info_web/";
    public static final String add_rating_web="add_rating_web/";
    public static final String get_satisfied_status_information="get_satisfied_status_information/";
    public static final String add_purchase_info_web="add_purchase_info_web/";
    public static final String add_delivered_order_status_web="add_delivered_order_status_web/";
    public static final String get_order_information_status="get_order_information_status/";

    //payphone
    public static final String get_payment_method_status_web="get_payment_method_status_web/";
    public static final String get_bank_account_info_list_web="get_bank_account_info_list_web/";
    public static final String get_admin_bank_account_info_web="get_admin_bank_account_info_web/";
    public static final String save_amount_to_pay_for_payphone_web="save_amount_to_pay_for_payphone_web/";
    public static final String payment_api_after_pay_phone_order_web="payment_api_after_pay_phone_order_web/";
    public static final String payment_api_bank_transfer_web="payment_through_bank_transfer_method_web/";

    //wallet
    public static final String wallet_commission_earned_web="get_wallet_commission_earned_web/";
    public static final String wallet_traveller_amount_web="get_wallet_traveller_amount_web/";

    public static final String social_login="login_with_social_web/";
    public static final String desc ="get_updated_appDynamicData_info/";
    public static final String DeletedUser = "check_user_deleted_or_not_by_admin_web/";
    public static final String LanguageUpdated ="update_user_language_web/";
}
