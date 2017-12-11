package bo.liu.demo;

import java.util.ArrayList;
import java.util.Date;


/**
 * 全局变量
 * Created by dlh on 2016/3/30 09:22.
 */
public class Constants {
    public static final String BAIDU_URL = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&ch=5&tn=98012088_5_dg&wd=百度地图下载&oq=%25E7%2599%25BE%25E5%25BA%25A6%25E5%259C%25B0%25E5%259B%25BE%25E4%25B8%258B%25E8%25BD%25BD&rsv_pq=dffd8b7700003f69&rsv_t=4bb3UMzvYjRVGH8HFOXwgb7AaNpQnttAtVIURH3H%2BdGhF%2F%2Fc06KNxyUyOdhv6tENM1sPlA&rqlang=cn&rsv_enter=1&inputT=1060&rsv_sug3=7&rsv_sug1=7&rsv_sug7=100&bs=百度地图下载&qq-pf-to=pcqq.c2c";

    public static final String DEFAULT_BEGINDATE = "1900-01-01 00:00:00";
    public static final String DEFAULT_ENDDATE = "2099-12-31";
    //连接超时时间
    public static final int ConnectTimeout = 15000;
    //客情赠送
    public static final String BACK_REASON_GIFT_CODE = "0001";
    //操作失误
    public static final String BACK_REASON_ERROR_CODE = "0002";
    //商品买赠
    public static final int TC_TYPE_PRODUCT_BUY_GIFT = 1;
    //现金买赠
    public static final int TC_TYPE_MONEY_BUY_GIFT = 2;

    /**
     * app标识字符串key
     */
    public static final String SP_APP_FLAG_KEY = "app_key";
    /**
     * 终端等级id
     */
    public static final String SP_TERMINAL_LEVEL_ID = "sp_terminal_level_id";
    /**
     * 为签到开单页面
     */
    public static final String SP_WQD_SALE_STATUS = "sp_wqd_sale_status";
    /**
     * 本地保存一份签到数据，方便主页面调用判断
     * 线路id
     */
    public static final String SP_SIGN_INFO_FOR_MAIN_LINEID = "signInfoForMainAct_lineID";
    /**
     * 本地保存一份签到数据，方便主页面调用判断
     * 终端id
     */
    public static final String SP_SIGN_INFO_FOR_MAIN_TERMINALID = "signInfoForMainAct_terminalID";
    /**
     * 本地保存一份签到数据，方便主页面调用判断
     * 终端名称
     */
    public static final String SP_SIGN_INFO_FOR_MAIN_TERMINALNAME = "signInfoForMainAct_terminalName";
    /**
     * 本地保存一份签到数据，方便主页面调用判断
     * 签到时间
     */
    public static final String SP_SIGN_INFO_FOR_MAIN_SIGNDATE = "signInfoForMainAct_signDate";
    /**
     * 本地保存一份签到数据，方便主页面调用判断
     * 终端签到状态 1已经签到 2签退 -1 默认值
     */
    public static final String SP_SIGN_INFO_FOR_MAIN_SIGNSTATUS = "signInfoForMainAct_signStatus";
    /**
     * 存储一份 照片的uuid  同一家终端 签到签退 的照片的uuid 是一个
     */
    public static final String SP_IMAGE_NAME_UUID = "terminal_image_name_uuid";
    /**
     * 竞品图片uuid
     */
    public static final String SP_JP_IMAGE_NAME_UUID = "sp_jp_image_name_uuid";

    /**
     * 记住密码key
     */
    public static final String SP_REMEMBER_PASSWORD_KEY = "rememberpassword_key";
    /**
     * 自动登录key
     */
    public static final String SP_AUTO_LOGIN_KEY = "autologin_key";
    /**
     * 网络请求的token
     */
    public static final String SP_APP_NET_REQUEST_TOKEN = "sp_app_net_request_token";
    /**
     * 用户名key
     */
    public static final String SP_USERNAME_KEY = "username_key";
    /**
     * 密码key
     */
    public static final String SP_PASSWORD_KEY = "password_key";
    /**
     * 签到拜访-是否当天第一次拜访签到
     */
    public static final String SP_SIGN_DAYFIRST_OPEN_DL = "sign_dayfirst_open_dl";
    /**
     * 签到拜访-获取路线类型（按距离）
     */
    public static final String SP_SIGN_XLSELECT_TYPE = "sign_xlselect_type";
    /**
     * 临期提醒测试字符串key
     */
    public static final String SP_TEST_LQTX = "lqtx_test_str";
    /**
     * 还货提醒 字符串
     */
    public static final String SP_TEST_HH = "hh_test_str";
    /**
     * 物料回收测试字符串key
     */
    public static final String SP_TEST_TX = "wltx_test_str";
    /**
     * 数据同步，当正在下载或上传时，置为true，默认false，当为true时DataSyncFragment中的initFragment()方法
     * /***
     * 以下是适配Android6.0以上系统的权限请求码
     ***/
    //通讯录
    public static final int READ_CONTACTS = 1;
    //打电话
    public static final int CALL_PHONE = 2;
    //相机
    public static final int CAMEAR = 3;
    //SD卡读写
    public static final int WRITE_EXTERNAL_STORAGE = 4;
    /**
     * 是否升级apk
     */
    public static final int WHAT_UPGRADE = 1;
    /**
     * 签到 签退标识
     */
    public static final int SIGN_IN = 1;
    public static final int SIGN_OUT = 2;
    /**
     * 签到结果 1成功
     */
    public static final int SIGN_RESULT = 1;
    public static final int LOCATION_OK = 1;
    public static final int DELETE_OK = 2;
    /***************************
     * 选择销售类型：销，退，换，送
     **************************/
    public static final String SALE_TYPE = "SALE_TYPE";
    public static final int SALE_TYPE_XIAO = 0;
    public static final int SALE_TYPE_BACK = 1;
    public static final int SALE_TYPE_HUAN = 2;
    public static final int SALE_TYPE_GIFT = 3;

    /***************************
     * 订单模式选择销售类型：销，退，换，送
     **************************/
    public static final String ORDER_SALE_TYPE = "ORDER_SALE_TYPE";
    public static final int ORDER_SALE_TYPE_XIAO = 0;
    public static final int ORDER_SALE_TYPE_BACK = 1;
    public static final int ORDER_SALE_TYPE_HUAN = 2;
    public static final int ORDER_SALE_TYPE_GIFT = 3;
    public static final String XSJS = "XSJS";
    /***
     * pda_type
     ***/
    public static final String MDPK = "MDPK";              //门店盘库
    public static final String QD = "QD";                //签到
    public static final String JFDH = "JFDH";            //积分兑换
    public static final String JFTH = "JFTH";            //积分退还
    public static final String JFTHXJ = "JFTHXJ";        //积分退还现金
    public static final String JFTHSP = "JFTHSP";        //积分退还商品
    public static final String KSJ = "KSJ";
    public static final String JFDHXJ = "JFDHXJ";        //积分兑换现金
    public static final String JFDHSP = "JFDHSP";        //积分兑换商品

    public static final String DPXS = "DPXS";            //单品销售
    public static final String TCXS = "TCXS";            //套餐销售
    public static final String DPTH = "DPTH";            //单品退货
    public static final String TCTH = "TCTH";            //套餐退货
    public static final String YSW = "YSW";              //已售完
    public static final String HHR = "HHR";              //换货入
    public static final String HHC = "HHC";              //换货出
    public static final String JH = "JH";                //借货
    public static final String HH = "HH";                //还货
    public static final String KQZS = "KQZS";            //客情赠送
    public static final String DHHXS = "DHHXS";          //订货会销售
    public static final String DHHTH = "DHHTH";          //订货会退货
    public static final String RWFL = "RWFL";            //任务返利
    public static final String RWFLXJ = "RWFLXJ";        //任务返利现金
    public static final String RWFLSP = "RWFLSP";        //任务返利商品

    public static final String CLFL = "CLFL";            //陈列返利
    public static final String CLFLXJ = "CLFLXJ";        //陈列返利现金
    public static final String CLFLSP = "CLFLSP";        //陈列返利商品


    public static final String WLTF = "WLTF";            //物料投放
    public static final String WLHS = "WLHS";            //物料回收
    public static final String WLDHR = "WLDHR";          //物料兑换入
    public static final String WLDHC = "WLDHC";          //物料兑换出
    public static final String WLDHXJ = "WLDHXJ";        //物料兑换现金
    public static final String ZCJH = "ZCJH";            //装车计划
    public static final String THJH = "THJH";            //退货计划
    public static final String JHTZ = "JHTZ";            //计划调整
    public static final String DDTZ = "DDTZ";            //订单调整
    public static final String SQK = "SQK";              //收欠款
    public static final String XSSK = "XSSK";            //销售收款
    public static final String FF = "FF";            //分发


    public static final int ZP_LABLE = 0;//正品lable
    public static final int LQ_LABLE = 1;//临期lable
    public static final int FH_LABLE = 2;//返货lable
    public static final int TH_LABLE = 3;//退货lable


    public static final String SGIN_TOAST_TXT = "请先签到...";
    public static final String TALLY_NO_STANDARD_DISPLAY_TXT = "无标准陈列...";
    public static final String TOAST_REDEEM_INTEGRAL_SELECT_QUCIK_SEARCH = "请先选择积分兑换方案...";
    public static final String OVER_PRODUCT_STOCK_NUM_TOAST_TXT = "库存数量不足,请重新输入当前总库存数量为:";
    public static final String OVER_INTEGRAL_STOCK_NUM_TOAST_TXT = "可用积分数量不足，请修改";
    public static final String CLOSE_ACTIVITY_DATA_DONT_FULL_XTX = "返回后，编辑区数据会清空，确认返回？";
    public static final String CLOSE_ACTIVITY_DATA_CLEAR_PRODUCT_XTX = "返回后,会清空已选商品,确定返回吗?";
    public static final String DELECT_ZD_SAVE_DATA_TXT = "确定要删除该类型吗?";
    public static final String DATA_CLEAR_TXT = "数据已清空!";
    public static final String SET_TERMINAL_USE_JIFEN_DH = "set_terminal_use_jifen_count_dh";
    public static final String SET_TERMINAL_USE_JIFEN_TH = "set_terminal_use_jifen_count_th";
    //****************************车销开单所需标示******************************/
    public static final String WHERE_COME_FROM = "activity_flag";
    //标示从选择商品或者选择套餐界面跳转到编辑界面
    public static final int COME_FROM_SEL_PRODUCT = 0;
    //标示从开单明细界面  跳转到编辑界面
    public static final int COME_FROM_SALE_LIST = 1;
    //将单品设置为选中
    public static final int DANPIN_FLAG = 0;
    //将套餐frament设置为选中
    public static final int TAOCAN_FLAG = 1;
    //默认正常返回
    public static final int BACK = 3;
    // ****************************车销开单所需标示******************************/
    //套餐类型
    public static final String GDGM = "GDGM";//固定购买
    //基本单位标示
    public static final int BASIC_UNIT_FLAG = 1;


    //***********************************************************************************/
    public static final String GDGM_XL = "GDGM_XL";//固定购买，优惠 + 赠品是否启用系列
    public static final String LHGM = "LHGM";//灵活购买+赠品或者优惠 + 赠品是否启用系列
    public static final String LHGM_XL = "LHGM_XL";//灵活购买启用系列 + 商品启用系列  + 赠品是否启用系列
    public static final String ZHGM = "ZHGM";//组合购买 优惠或者赠品 + 赠品是否启用系列
    //物料数据
    public static final String MET_CAR = "MET_CAR";
    public static final String MET_HOSE = "MET_HOSE";
    public static final String MET_POSION = "MET_POSION";
    public static final String MATERIALPUTENTITY = "MATERIALPUTENTITY";
    public static final int MATERIAL = 1110;
    public static final int MATERIALH = 1111;
    public static final String XSJSDJ = "XSJSDJ";//结算标记
    public static final String XSJSMX = "XSJSMX";//结算明细
    public static final int CX = 0;
    public static final int DD = 1;

    //套餐数据标示
    public static final int TC_DATA = 0;
    //套餐销标示
    public static final int TC_SALE_DATA = 1;
    //套餐退标示
    public static final int TC_BACK_DATA = -1;
    /*****************************
     * 套餐相关
     ********************************/
    public static final int SPMZ = 1;//商品买赠 以商品数量为准
    public static final int XJMA = 2;//现金买赠 以金额为准
    /*************************************************************/
    //车销OSG_FLAG
    public static final int SALE_OSG_FLAG = 0;
    //订单OSG_FLAG
    public static final int ORDER_SALE_OSG_FLAG = 1;
    /**
     * 报表选中起始日期
     */
    public static String beginDate = "";
    public static String endDate = "";
    public static String visitBeginDate = "";
    public static String visitEndDate = "";
    public static String terminalBeginDate = "";
    public static String terminalEndDate = "";
    /**
     * 查询价格管理方案类型标示
     */
    public static final int OFFER_PRICE_TYPE = 0;//结算优惠标示
    public static final int SALE_PRICE_TYPE = 1;//开单修改价格标示
    public static final int ML_PRICE_TYPE = 2;//抹零模式
    /**
     * 借货还货  任务返利 共用标识
     */
    public static final String COME_FROM_FLAG = "activity_flag";
    //标识选择界面
    public static final int SELECTE_ACTIVITY_FLAG = 0;
    // 标识编辑界面
    public static final int EDIT_ACTIVITY_FLAG = 1;
    //标识从开单明细界面（浏览界面）
    public static final int LIST_ACTIVITY_FLAG = 2;
    // list传值标识
    public static final String EXTRA_LIST_KEY = "mSelectProductEntities";
    public static final String EXTRA_POSITION_KEY = "position";

    /************************************************************/
    //
    public static final String ISCHECKEPRICEPLAN = "isCheckePricePlan";
    /**
     * 退换货原因  和  赠送原因标示
     */
    public static final int BACK_REASON = 0;
    public static final int GIFT_REASON = 1;
    //视图sql语句字符串，创建车辆商品明细视图
    public static final String SQL_VIEW_PRODUCT_DETAIL_CAR = "CREATE VIEW  vw_product_detail_quantity AS " +
            " SELECT\n" +
            " 0 AS RdFlag,\n" +
            " tb_product.product_name,\n" +
            " tb_product_stock_car.product_id,\n" +
            " tb_product.product_spec_g,\n" +
            " tb_product_stock_car.iquantitycon,\n" +
            " tb_product_stock_car.label_id,\n" +
            " tb_product_stock_car.update_time,\n" +
            " tb_product_stock_car.batch\n" +
            " FROM\n" +
            " tb_product_stock_car\n" +
            " INNER JOIN tb_product ON tb_product_stock_car.product_id = tb_product.id\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_product.product_name,\n" +
            " tb_up_sfa_sales.product_id,\n" +
            " tb_product.product_spec,\n" +
            " tb_up_sfa_sales.ba_quantity_con,\n" +
            " tb_up_sfa_sales.label_id,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_product_stock_car\n" +
            " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
            " WHERE\n" +
            " (\n" +
            " tb_up_sfa_sales.pda_type = 'DPXS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'TCXS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'HHC'\n" +
            " OR tb_up_sfa_sales.pda_type = 'KQZS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'HH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'DHHXS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'RWFLSP'\n" +
            " OR tb_up_sfa_sales.pda_type = 'CLFLSP'\n" +
            " OR tb_up_sfa_sales.pda_type = 'WLDHC'\n" +
            " OR tb_up_sfa_sales.pda_type = 'JFDHSP'\n" +
            " OR tb_up_sfa_sales.pda_type = 'THJH'\n" +
            " )\n" +
            " AND tb_up_sfa_sales.idate > tb_product_stock_car.create_time\n" +
            " AND tb_up_sfa_sales.osg_flag = 0\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_product.product_name,\n" +
            " tb_up_sfa_sales.product_id_g,\n" +
            " tb_product.product_spec,\n" +
            " tb_up_sfa_sales.ba_quantity_con_g,\n" +
            " tb_up_sfa_sales.label_id_g,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code_g\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_product_stock_car\n" +
            " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id_g = tb_product.id\n" +
            " WHERE\n" +
            " (\n" +
            " tb_up_sfa_sales.pda_type = 'DPXS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'TCXS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'KQZS'\n" +
            " OR tb_up_sfa_sales.pda_type = 'DHHXS'\n" +
            " )\n" +
            " AND tb_up_sfa_sales.idate > tb_product_stock_car.create_time\n" +
            " AND tb_up_sfa_sales.osg_flag = 0\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_product.product_name,\n" +
            " tb_up_sfa_sales.product_id,\n" +
            " tb_product.product_spec,\n" +
            " tb_up_sfa_sales.ba_quantity_con,\n" +
            " tb_up_sfa_sales.label_id,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_product_stock_car\n" +
            " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
            " WHERE\n" +
            " (\n" +
            " tb_up_sfa_sales.pda_type = 'DPTH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'TCTH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'HHR'\n" +
            " OR tb_up_sfa_sales.pda_type = 'JH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'DHHTH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'JFTHSP'\n" +
            " )\n" +
            " AND tb_up_sfa_sales.idate > tb_product_stock_car.create_time\n" +
            " AND tb_up_sfa_sales.osg_flag = 0\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_product.product_name,\n" +
            " tb_up_sfa_sales.product_id_g,\n" +
            " tb_product.product_spec,\n" +
            " tb_up_sfa_sales.ba_quantity_con_g,\n" +
            " tb_up_sfa_sales.label_id_g,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code_g\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_product_stock_car\n" +
            " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id_g = tb_product.id\n" +
            " WHERE\n" +
            " (\n" +
            " tb_up_sfa_sales.pda_type = 'DPTH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'TCTH'\n" +
            " OR tb_up_sfa_sales.pda_type = 'DHHTH'\n" +
            " )\n" +
            " AND tb_up_sfa_sales.idate > tb_product_stock_car.create_time\n" +
            " AND tb_up_sfa_sales.osg_flag = 0";
    //创建车辆商品真实库存视图
    public static final String SQL_VIEW_PRODUCT_REAL_CAR = "CREATE VIEW vw_product_real_quantity AS" +
            " SELECT\n" +
            " product_id,\n" +
            " sum(\n" +
            " CASE\n" +
            " WHEN vwii.RdFlag = 0 THEN\n" +
            " vwii.iquantitycon\n" +
            " ELSE\n" +
            " 0 - vwii.iquantitycon\n" +
            " END\n" +
            " ) AS iquantity_con2,\n" +
            " product.p_category_id AS category_id,\n" +
            " category.pro_category_name AS cInvCCName\n" +
            " FROM\n" +
            " vw_product_detail_quantity vwii\n" +
            " INNER JOIN tb_product product ON vwii.product_id = product.id\n" +
            " INNER JOIN tb_product_category category ON product.p_category_id = category.id\n" +
            " WHERE\n" +
            " (label_id = 0 OR label_id = 1)\n" +
            " AND iquantitycon > 0\n" +
            " AND use_state == 1" +
            " GROUP BY\n" +
            " product_id,\n" +
            " product.p_category_id,\n" +
            " category.pro_category_name";
    //创建退货计划专用车辆商品真实库存视图
    public static final String SQL_VIEW_PRODUCT_GARAGE_CAR = "CREATE VIEW vw_product_garage_quantity AS" +
            " SELECT\n" +
            " product_id,\n" +
            " sum(\n" +
            " CASE\n" +
            " WHEN vwii.RdFlag = 0 THEN\n" +
            " vwii.iquantitycon\n" +
            " ELSE\n" +
            " 0 - vwii.iquantitycon\n" +
            " END\n" +
            " ) AS iquantity_con2,\n" +
            " product.p_category_id AS category_id,\n" +
            " category.pro_category_name AS cInvCCName\n" +
            " FROM\n" +
            " vw_product_detail_quantity vwii\n" +
            " INNER JOIN tb_product product ON vwii.product_id = product.id\n" +
            " INNER JOIN tb_product_category category ON product.p_category_id = category.id\n" +
            " WHERE\n" +
            " (label_id = 0 OR label_id = 1)\n" +
            " AND iquantitycon > 0\n" +
            " GROUP BY\n" +
            " product_id,\n" +
            " product.p_category_id,\n" +
            " category.pro_category_name";
    //***********************************************创建仓库库存详情视图SQL********************************************/
    public static final String SQL_VIEW_PRODUCT_WAREHOUSE_STOCK_DETAIL = "CREATE VIEW vw_product_detail_quantity_wh AS" +
            " SELECT 0 AS RdFlag,\n" +
            " tb_product.product_name,\n" +
            " tb_product_stock.product_id,\n" +
            " tb_product.product_spec_g,\n" +
            " tb_product_stock.iquantitycon,\n" +
            " tb_product_stock.update_time,\n" +
            " tb_product_stock.batch_code,\n" +
            " tb_product_stock.ware_house_id\n" +
            " FROM\n" +
            " tb_product_stock\n" +
            " INNER JOIN tb_product ON tb_product_stock.product_id = tb_product.id\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_product.product_name,\n" +
            " tb_up_sfa_sales.product_id,\n" +
            " tb_product.product_spec,\n" +
            " tb_up_sfa_sales.ba_quantity_con,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code,\n" +
            " tb_up_sfa_sales.ware_house_id FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_product_stock\n" +
            " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
            " WHERE\n" +
            " (tb_up_sfa_sales.pda_type = 'DPXS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'HHC' \n" +
            " OR tb_up_sfa_sales.pda_type = 'KQZS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'HH' \n" +
            " OR tb_up_sfa_sales.pda_type = 'TCXS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'DHHXS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'RWFLSP' \n" +
            " OR tb_up_sfa_sales.pda_type = 'CLFLSP' \n" +
            " OR tb_up_sfa_sales.pda_type = 'JFDHSP' \n" +
            " OR tb_up_sfa_sales.pda_type = 'WLDHC' \n" +
            " OR tb_up_sfa_sales.pda_type = 'ZCJH') \n" +
            " AND tb_up_sfa_sales.idate > tb_product_stock.create_time\n" +
            " AND tb_up_sfa_sales.osg_flag = 1\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_product.product_name,\n" +
            " tb_up_sfa_sales.product_id_g,\n" +
            " tb_product.product_spec,\n" +
            " tb_up_sfa_sales.ba_quantity_con_g,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code_g, tb_up_sfa_sales.ware_house_id FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_product_stock\n" +
            " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id_g = tb_product.id\n" +
            " WHERE\n" +
            " (tb_up_sfa_sales.pda_type = 'DPXS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'TCXS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'KQZS' \n" +
            " OR tb_up_sfa_sales.pda_type = 'DHHXS') \n" +
            " AND tb_up_sfa_sales.idate > tb_product_stock.create_time\n" +
            " AND tb_up_sfa_sales.osg_flag = 1";
    //*******************************************创建仓库库存实际库存量视图SQL************************************************************/
    public static final String SQL_VIEW_CREATE_REAL_WAREHOUSE_STOCK = "CREATE VIEW vw_product_real_quantity_wh AS" +
            " SELECT\n" +
            " product_id,\n" +
            " sum(\n" +
            " CASE\n" +
            " WHEN vwii.RdFlag = 0 THEN\n" +
            " vwii.iquantitycon\n" +
            " ELSE\n" +
            " 0 - vwii.iquantitycon\n" +
            " END\n" +
            " ) AS iquantitycon,\n" +
            " product.p_category_id AS category_id,\n" +
            " category.pro_category_name AS cInvCCName\n" +
            " FROM\n" +
            " vw_product_detail_quantity_wh vwii\n" +
            " INNER JOIN tb_product product ON vwii.product_id = product.id\n" +
            " INNER JOIN tb_product_category category ON product.p_category_id = category.id\n" +
            " WHERE\n" +
            " iquantitycon > 0\n" +
            " GROUP BY\n" +
            " product_id,\n" +
            " product.p_category_id,\n" +
            " category.pro_category_name";
    public static final String SQL_VIEW_MATERIAL_DETAIL_CAR = "CREATE VIEW vw_material_detail_quantity AS" +
            " SELECT\n" +
            " 0 AS RdFlag,\n" +
            " tb_material.material_name,\n" +
            " tb_material_stock_car.material_id,\n" +
            " tb_material.materia_spec_g,\n" +
            " tb_material_stock_car.iquantitycon2,\n" +
//            " tb_material_stock_car.production_date,\n" +
//            " tb_material_stock_car.region_id,\n" +
            " tb_material_stock_car.label_id,\n" +
            " tb_material_stock_car.update_time,\n" +
            " tb_material_stock_car.batch\n" +
            " FROM\n" +
            " tb_material_stock_car\n" +
            " INNER JOIN tb_material ON tb_material_stock_car.material_id = tb_material.id\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_material.material_name,\n" +
            " tb_up_sfa_sales.product_id,\n" +
            " tb_material.materia_spec_g,\n" +
            " tb_up_sfa_sales.ba_quantity_con,\n" +
//            " tb_up_sfa_sales.prodate,\n" +
//            " tb_up_sfa_sales.origin_id,\n" +
            " tb_up_sfa_sales.label_id,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_material_stock_car\n" +
            " LEFT JOIN tb_material ON tb_up_sfa_sales.product_id = tb_material.id\n" +
            " WHERE\n" +
            " (tb_up_sfa_sales.pda_type = 'WLTF'\n" +
            " OR (\n" +
            " tb_up_sfa_sales.pda_type = 'THJH'\n" +
            " AND tb_up_sfa_sales.itemType = 1\n" +
            " ))\n" +
            " AND tb_up_sfa_sales.osg_flag = 0\n" +
            " AND tb_up_sfa_sales.idate > tb_material_stock_car.create_time\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_material.material_name,\n" +
            " tb_up_sfa_sales.product_id,\n" +
            " tb_material.materia_spec_g,\n" +
            " tb_up_sfa_sales.ba_quantity_con,\n" +
//            " tb_up_sfa_sales.prodate,\n" +
//            " tb_up_sfa_sales.origin_id,\n" +
            " tb_up_sfa_sales.label_id,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_material_stock_car\n" +
            " LEFT JOIN tb_material ON tb_up_sfa_sales.product_id = tb_material.id\n" +
            " WHERE\n" +
            " tb_up_sfa_sales.pda_type = 'WLHS'\n" +
            " AND tb_up_sfa_sales.osg_flag = 0\n" +
            " AND tb_up_sfa_sales.idate > tb_material_stock_car.create_time;";
    //******************************************创建仓库物料库存详情视图*****************************************/
    public static final String SQL_VIEW_MATERIAL_DETAIL_WH = "CREATE VIEW vw_material_detail_quantity_wh AS" +
            " SELECT\n" +
            " 0 AS RdFlag,\n" +
            " tb_material.material_name,\n" +
            " tb_material_stock.material_id,\n" +
            " tb_material.materia_spec_g,\n" +
            " tb_material_stock.fz_pre_stock_num,\n" +
//            " tb_material_stock.pro_date,\n" +
//            " tb_material_stock.org_id,\n" +
            " tb_material_stock.update_time,\n" +
            " tb_material_stock.batch_code,\n" +
            " tb_material_stock.ware_house_id\n" +
            " FROM\n" +
            " tb_material_stock\n" +
            " INNER JOIN tb_material ON tb_material_stock.material_id = tb_material.id\n" +
            " UNION ALL\n" +
            " SELECT DISTINCT\n" +
            " tb_up_sfa_sales.rd_flag,\n" +
            " tb_material.material_name,\n" +
            " tb_up_sfa_sales.product_id,\n" +
            " tb_material.materia_spec_g,\n" +
            " tb_up_sfa_sales.ba_quantity_con,\n" +
//            " tb_up_sfa_sales.prodate,\n" +
//            " tb_up_sfa_sales.origin_id,\n" +
            " tb_up_sfa_sales.idate,\n" +
            " tb_up_sfa_sales.batch_code,\n" +
            " tb_up_sfa_sales.ware_house_id\n" +
            " FROM\n" +
            " tb_up_sfa_sales\n" +
            " INNER JOIN tb_material_stock\n" +
            " LEFT JOIN tb_material ON tb_up_sfa_sales.product_id = tb_material.id\n" +
            " WHERE\n" +
            " (tb_up_sfa_sales.pda_type = 'WLTF'\n" +
            " OR (\n" +
            " tb_up_sfa_sales.pda_type = 'ZCJH'\n" +
            " AND tb_up_sfa_sales.itemType = 1\n" +
            " ))\n" +
            " AND tb_up_sfa_sales.osg_flag = 1\n" +
            " AND tb_up_sfa_sales.idate > tb_material_stock.create_time;\n";
    //创建当天终端店退货到车库商品和数量视图
    public static final String SQL_VIEW_RETURN_GOODS = "CREATE VIEW vw_return_goods AS" +
            " SELECT DISTINCT" +
            " product_id," +
            " product_name," +
            " product_spec_g," +
            " p_category_id," +
            " pro_date," +
            " v_org_code," +
            " sale_type," +
            " return_type," +
            " sum(fz_pre_stock_num) AS fz_pre_stock_num" +
            " FROM" +
            " (" +
            " SELECT DISTINCT" +
            " tb_product.id AS product_id," +
            " tb_product.product_name," +
            " tb_product.product_spec_g," +
            " tb_product.p_category_id," +
            " pro_date," +
            " v_org_code," +
            " sale_type," +
            " return_type," +
            " sum(" +
            " tb_local_save.i_quantity_1 * tb_product_unit.pu_realation + tb_local_save.i_quantity_2" +
            " ) AS fz_pre_stock_num" +
            " FROM" +
            " tb_local_save" +
            " INNER JOIN tb_product ON tb_local_save.product_id = tb_product.id" +
            " INNER JOIN tb_product_unit ON tb_local_save.pro_unit_id = tb_product_unit.id" +
            " WHERE" +
            " pda_type = 'DPTH'" +
            " OR pda_type = 'TCTH'" +
            " AND osg_flag = 2" +
            " AND tb_local_save.create_date > 0" +
            " GROUP BY" +
            " tb_product.id," +
            " pro_date," +
            " return_type" +
            " UNION ALL" +
            " SELECT DISTINCT" +
            " tb_product.id AS product_id," +
            " tb_product.product_name," +
            " tb_product.product_spec_g," +
            " tb_product.p_category_id," +
            " pro_date_g AS pro_date," +
            " v_org_code_g AS v_org_code," +
            " return_type," +
            " sum(" +
            " tb_local_save.i_quantity_1_g * tb_product_unit.pu_realation + tb_local_save.i_quantity_2_g" +
            " ) AS fz_pre_stock_num" +
            " FROM" +
            " tb_local_save" +
            " INNER JOIN tb_product ON tb_local_save.product_id = tb_product.id" +
            " INNER JOIN tb_product_unit ON tb_local_save.pro_unit_id = tb_product_unit.id" +
            " WHERE" +
            " pda_type = 'DPTH'" +
            " OR pda_type = 'TCTH'" +
            " OR pda_type = 'HHR'" +
            " AND osg_flag = 2" +
            " AND tb_local_save.create_date > 0" +
            " GROUP BY" +
            " tb_product.id," +
            " pro_date," +
            " return_type" +
            " )" +
            " GROUP BY" +
            " product_id," +
            " pro_date," +
            " return_type";
    //创建当天车销销售商品和数量视图
    public static final String SQL_VIEW_SALE_GOODS = "CREATE VIEW vw_sale_goods AS" +
            " SELECT DISTINCT" +
            " product_id," +
            " product_name," +
            " product_spec_g," +
            " p_category_id," +
            " pro_date," +
            " v_org_code," +
            " sum(fz_pre_stock_num) AS fz_pre_stock_num" +
            " FROM" +
            " (" +
            " SELECT DISTINCT" +
            " tb_product.id AS product_id," +
            " tb_product.product_name," +
            " tb_product.product_spec_g," +
            " tb_product.p_category_id," +
            " pro_date," +
            " v_org_code," +
            " sum(" +
            " tb_local_save.i_quantity_1 * tb_product_unit.pu_realation + tb_local_save.i_quantity_2" +
            " ) AS fz_pre_stock_num" +
            " FROM" +
            " tb_local_save" +
            " INNER JOIN tb_product ON tb_local_save.product_id = tb_product.id" +
            " INNER JOIN tb_product_unit ON tb_local_save.pro_unit_id = tb_product_unit.id" +
            " WHERE" +
            " pda_type = 'DPXS'" +
            " OR pda_type = 'TCXS'" +
            " OR pda_type = 'HHC'" +
            " AND osg_flag = 2" +
            " AND tb_local_save.create_date > 0" +
            " GROUP BY" +
            " tb_product.id," +
            " pro_date" +
            " UNION ALL" +
            " SELECT DISTINCT" +
            " tb_product.id AS product_id," +
            " tb_product.product_name," +
            " tb_product.product_spec_g," +
            " tb_product.p_category_id," +
            " pro_date_g AS pro_date," +
            " v_org_code_g AS v_org_code," +
            " sum(" +
            " tb_local_save.i_quantity_1_g * tb_product_unit.pu_realation + tb_local_save.i_quantity_2_g" +
            " ) AS fz_pre_stock_num" +
            " FROM" +
            " tb_local_save" +
            " INNER JOIN tb_product ON tb_local_save.product_id = tb_product.id" +
            " INNER JOIN tb_product_unit ON tb_local_save.pro_unit_id = tb_product_unit.id" +
            " WHERE" +
            " pda_type = 'DPXS'" +
            " OR pda_type = 'TCXS'" +
            " OR pda_type = 'HHC'" +
            " AND osg_flag = 2" +
            " AND tb_local_save.create_date > 0" +
            " GROUP BY" +
            " tb_product.id," +
            " pro_date" +
            " )" +
            " GROUP BY" +
            " product_id," +
            " pro_date";
    //创建今日退货视图
    public static final String SQL_VIEW_TODAY_RETURN_GOODS = "CREATE VIEW vw_today_return_goods AS" +
            " SELECT DISTINCT" +
            " vw_return_goods.product_id," +
            " vw_return_goods.product_name," +
            " vw_return_goods.product_spec_g," +
            " vw_return_goods.p_category_id," +
            " vw_return_goods.pro_date," +
            " vw_return_goods.v_org_code," +
            " sum(" +
            " vw_return_goods.fz_pre_stock_num - vw_sale_goods.fz_pre_stock_num" +
            " ) AS fz_pre_stock_num" +
            " FROM" +
            " vw_return_goods" +
            " INNER JOIN vw_sale_goods ON vw_return_goods.product_id = vw_sale_goods.product_id" +
            " AND vw_return_goods.pro_date = vw_sale_goods.pro_date" +
            " AND vw_return_goods.return_type = 0" +
            " GROUP BY" +
            " vw_return_goods.product_id," +
            " vw_return_goods.pro_date" +
            " UNION ALL" +
            " SELECT DISTINCT" +
            " product_id," +
            " product_name," +
            " product_spec_g," +
            " p_category_id," +
            " pro_date," +
            " v_org_code," +
            " sum(fz_pre_stock_num)" +
            " FROM" +
            " vw_return_goods" +
            " WHERE" +
            " AND vw_return_goods.return_type = 0" +
            " GROUP BY" +
            " vw_return_goods.product_id," +
            " vw_return_goods.pro_date";
    //创建今日销售视图
    public static final String SQL_VIEW_TODAY_SALE_GOODS = "CREATE VIEW vw_today_sale_goods AS" +
            " SELECT DISTINCT" +
            " vw_sale_goods.product_id," +
            " vw_sale_goods.product_name," +
            " vw_sale_goods.product_spec_g," +
            " vw_sale_goods.p_category_id," +
            " vw_sale_goods.pro_date," +
            " vw_sale_goods.v_org_code," +
            " sum(" +
            " vw_product_detail_quantity.fz_pre_stock_num" +
            " ) AS fz_pre_stock_num" +
            " FROM" +
            " vw_sale_goods" +
            " INNER JOIN vw_product_detail_quantity " +
            " ON vw_sale_goods.product_id = vw_product_detail_quantity.product_id" +
            " GROUP BY" +
            " vw_sale_goods.product_id," +
            " vw_sale_goods.pro_date";
    //创建可退货库存量视图
    public static final String SQL_VIEW_RETURN_STOCK_DETAIL =
            "CREATE VIEW vw_product_sale_his_detail_quantity AS " +
                    //历史销售记录中加库存0加  1减
                    " SELECT\n" +
                    " 0 AS RdFlag,\n" +
                    " history.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    //  " history.prodate,\n" +
                    " history.batch_code,\n" +
                    //   " history.origin_id,\n" +
                    " history.ba_quantity_con,\n" +
                    " history.create_date,\n" +
                    " history.pda_type,\n" +
//                    " history.os_g_flag,\n" +
                    " history.terminal_id\n" +
                    " FROM\n" +
                    " tb_terminal_sell_history history\n" +
                    " LEFT JOIN tb_product ON history.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " (\n" +
                    " history.pda_type = 'DPXS'\n" +
                    " OR history.pda_type = 'TCXS'\n" +
                    " OR history.pda_type = 'KQZS'\n" +
                    " OR history.pda_type = 'HHC'\n" +
                    " OR history.pda_type = 'JFDHSP'\n" +
                    " OR history.pda_type = 'WLDHSP'\n" +
                    " OR history.pda_type = 'CLFLSP'\n" +
                    " OR history.pda_type = 'RWFLSP'\n" +
                    " OR history.pda_type = 'HH'\n" +
                    " )\n" +
                    " UNION ALL\n" +
                    //历史销售记录中减库存0加  1减
                    " SELECT\n" +
                    " 1 AS RdFlag,\n" +
                    " history.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    // " history.prodate,\n" +
                    " history.batch_code,\n" +
                    //  " history.origin_id,\n" +
                    " history.ba_quantity_con,\n" +
                    " history.create_date,\n" +
                    " history.pda_type,\n" +
//                    " history.os_g_flag,\n" +
                    " history.terminal_id\n" +
                    " FROM\n" +
                    " tb_terminal_sell_history history\n" +
                    " LEFT JOIN tb_product ON history.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " (\n" +
                    " history.pda_type = 'DPTH'\n" +
                    " OR history.pda_type = 'TCTH'\n" +
                    " OR history.pda_type = 'HHR'\n" +
                    " OR history.pda_type = 'JFTHSP'\n" +
                    " OR history.pda_type = 'JH'\n" +
                    " )\n" +
//                    " UNION ALL\n" +
//                    //本地销售记录中加商品库存0加  1减
//                    " SELECT DISTINCT\n" +
//                    " 0 AS RdFlag,\n" +
//                    " tb_up_sfa_sales.product_id,\n" +
//                    " tb_product.product_name,\n" +
//                    " tb_product.product_spec,\n" +
//                    " tb_up_sfa_sales.prodate,\n" +
//                    " tb_up_sfa_sales.batch_code,\n" +
//                    " tb_up_sfa_sales.origin_id,\n" +
//                    " tb_up_sfa_sales.ba_quantity_con,\n" +
//                    " tb_up_sfa_sales.idate,\n" +
//                    " tb_up_sfa_sales.pda_type,\n" +
//                    " tb_up_sfa_sales.osg_flag,\n" +
//                    " tb_up_sfa_sales.terminal_id\n" +
//                    " FROM\n" +
//                    " tb_up_sfa_sales\n" +
//                    " INNER JOIN tb_product_stock_car\n" +
//                    " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
//                    " WHERE\n" +
//                    " (\n" +
//                    " tb_up_sfa_sales.pda_type = 'DPXS'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'KQZS'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'TCXS'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'HHC'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'CLFLSP'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'JFDHSP'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'WLDHSP'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'RWFLSP'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'HH'\n" +
//                    " )\n" +
//                    " AND tb_up_sfa_sales.idate > tb_product_stock_car.update_time -- AND tb_up_sfa_sales.osg_flag = 0\n" +
//                    " UNION ALL\n" +
//                    //本地销售记录中加赠品 库存0加  1减
//                    " SELECT DISTINCT\n" +
//                    " 0 AS RdFlag,\n" +
//                    " tb_up_sfa_sales.product_id_g,\n" +
//                    " tb_product.product_name,\n" +
//                    " tb_product.product_spec_g,\n" +
//                    " tb_up_sfa_sales.prodate_g,\n" +
//                    " tb_up_sfa_sales.batch_code_g,\n" +
//                    " tb_up_sfa_sales.origin_id_g,\n" +
//                    " tb_up_sfa_sales.ba_quantity_con_g,\n" +
//                    " tb_up_sfa_sales.idate,\n" +
//                    " tb_up_sfa_sales.pda_type,\n" +
////                    " tb_up_sfa_sales.osg_flag,\n" +
//                    " tb_up_sfa_sales.terminal_id\n" +
//                    " FROM\n" +
//                    " tb_up_sfa_sales\n" +
//                    " INNER JOIN tb_product_stock_car\n" +
//                    " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
//                    " WHERE\n" +
//                    " (\n" +
//                    " tb_up_sfa_sales.pda_type = 'DPXS'\n" +
//                    " OR tb_up_sfa_sales.pda_type = 'TCXS'\n" +
//                    " )\n" +
//                    " AND tb_up_sfa_sales.idate > tb_product_stock_car.update_time -- AND tb_up_sfa_sales.osg_flag = 0 --需要减去的商品数量\n" +
                    " UNION ALL\n" +
                    //本地销售记录中减商品
                    " SELECT DISTINCT\n" +
                    " 1 AS RdFlag,\n" +
                    " tb_up_sfa_sales.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec,\n" +
                    //          " tb_up_sfa_sales.prodate,\n" +
                    " tb_up_sfa_sales.batch_code,\n" +
                    //       " tb_up_sfa_sales.origin_id,\n" +
                    " tb_up_sfa_sales.ba_quantity_con,\n" +
                    " tb_up_sfa_sales.idate,\n" +
                    " tb_up_sfa_sales.pda_type,\n" +
//                    " tb_up_sfa_sales.osg_flag,\n" +
                    " tb_up_sfa_sales.terminal_id\n" +
                    " FROM\n" +
                    " tb_up_sfa_sales\n" +
                    " INNER JOIN tb_terminal_sell_history\n" +
                    " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " (\n" +
                    " tb_up_sfa_sales.pda_type = 'DPTH'\n" +
                    " OR tb_up_sfa_sales.pda_type = 'TCTH'\n" +
                    " OR tb_up_sfa_sales.pda_type = 'HHR'\n" +
                    " OR tb_up_sfa_sales.pda_type = 'JFTHSP'\n" +
                    " OR tb_up_sfa_sales.pda_type = 'JH'\n" +
                    " )\n" +
                    " AND tb_up_sfa_sales.idate > tb_terminal_sell_history.create_date\n" +
                    " UNION ALL\n" +
                    //本地销售记录中减赠品
                    " SELECT DISTINCT\n" +
                    " 1 AS RdFlag,\n" +
                    " tb_up_sfa_sales.product_id_g,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    //       " tb_up_sfa_sales.prodate_g,\n" +
                    " tb_up_sfa_sales.batch_code_g,\n" +
                    //     " tb_up_sfa_sales.origin_id_g,\n" +
                    " tb_up_sfa_sales.ba_quantity_con_g,\n" +
                    " tb_up_sfa_sales.idate,\n" +
                    " tb_up_sfa_sales.pda_type,\n" +
//                    " tb_up_sfa_sales.osg_flag,\n" +
                    " tb_up_sfa_sales.terminal_id\n" +
                    " FROM\n" +
                    " tb_up_sfa_sales\n" +
                    " INNER JOIN tb_terminal_sell_history\n" +
                    " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id_g = tb_product.id\n" +
                    " WHERE\n" +
                    " (\n" +
                    " tb_up_sfa_sales.pda_type = 'DPTH'\n" +
                    " OR tb_up_sfa_sales.pda_type = 'TCTH'\n" +
                    " )\n" +
                    " AND tb_up_sfa_sales.idate > tb_terminal_sell_history.create_date";
    /*************************************************************************************/
    //创建订货会退货库存量视图
    public static final String SQL_VIEW_DHH_RETURN_STOCK_DETAIL =
            "CREATE VIEW vw_product_dhh_his_detail_quantity AS " +
                    //历史销售记录中加库存0加  1减
                    " SELECT\n" +
                    " 0 AS RdFlag,\n" +
                    " history.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    " history.batch_code,\n" +
                    " history.ba_quantity_con,\n" +
                    " history.create_date,\n" +
                    " history.pda_type,\n" +
                    " history.terminal_id,\n" +
                    " history.dhh_series_id," +
                    " history.is_gift," +
                    " history.tc_id" +
                    " FROM\n" +
                    " tb_terminal_sell_history history\n" +
                    " LEFT JOIN tb_product ON history.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " history.pda_type = 'DHHXS'\n" +
                    " and history.is_gift = 0\n" +
                    " UNION ALL\n" +
                    //历史销售记录中加库存0加  1减
                    " SELECT\n" +
                    " 0 AS RdFlag,\n" +
                    " history.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    " history.batch_code,\n" +
                    " history.ba_quantity_con,\n" +
                    " history.create_date,\n" +
                    " history.pda_type,\n" +
                    " history.terminal_id,\n" +
                    " history.dhh_series_id," +
                    " history.is_gift," +
                    " history.tc_id" +
                    " FROM\n" +
                    " tb_terminal_sell_history history\n" +
                    " LEFT JOIN tb_product ON history.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " history.pda_type = 'DHHXS'\n" +
                    " and history.is_gift = 1\n" +
                    " UNION ALL\n" +
                    //历史销售记录中减库存0加  1减
                    " SELECT\n" +
                    " 1 AS RdFlag,\n" +
                    " history.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    " history.batch_code,\n" +
                    " history.ba_quantity_con,\n" +
                    " history.create_date,\n" +
                    " history.pda_type,\n" +
                    " history.terminal_id,\n" +
                    " history.dhh_series_id," +
                    " history.is_gift," +
                    " history.tc_id" +
                    " FROM\n" +
                    " tb_terminal_sell_history history\n" +
                    " LEFT JOIN tb_product ON history.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " history.pda_type = 'DHHTH'\n" +
                    " and history.is_gift = 0\n" +
                    " UNION ALL\n" +
                    //历史销售记录中减库存0加  1减
                    " SELECT\n" +
                    " 1 AS RdFlag,\n" +
                    " history.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    " history.batch_code,\n" +
                    " history.ba_quantity_con,\n" +
                    " history.create_date,\n" +
                    " history.pda_type,\n" +
                    " history.terminal_id,\n" +
                    " history.dhh_series_id," +
                    " history.is_gift," +
                    " history.tc_id" +
                    " FROM\n" +
                    " tb_terminal_sell_history history\n" +
                    " LEFT JOIN tb_product ON history.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " history.pda_type = 'DHHTH'\n" +
                    " and history.is_gift = 1\n" +
                    " UNION ALL\n" +
                    //本地销售记录中减商品
                    " SELECT DISTINCT\n" +
                    " 1 AS RdFlag,\n" +
                    " tb_up_sfa_sales.product_id,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec,\n" +
                    " tb_up_sfa_sales.batch_code,\n" +
                    " tb_up_sfa_sales.ba_quantity_con,\n" +
                    " tb_up_sfa_sales.idate,\n" +
                    " tb_up_sfa_sales.pda_type,\n" +
                    " tb_up_sfa_sales.terminal_id,\n" +
                    " tb_terminal_sell_history.dhh_series_id," +
                    " tb_terminal_sell_history.is_gift," +
                    " tb_terminal_sell_history.tc_id" +
                    " FROM\n" +
                    " tb_up_sfa_sales\n" +
                    " INNER JOIN tb_terminal_sell_history\n" +
                    " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id = tb_product.id\n" +
                    " WHERE\n" +
                    " tb_up_sfa_sales.pda_type = 'DHHTH'\n" +
                    " AND tb_up_sfa_sales.idate > tb_terminal_sell_history.create_date\n" +
                    " AND tb_terminal_sell_history.tc_id = tb_up_sfa_sales.scheme_id" +
                    " AND tb_terminal_sell_history.dhh_series_id = tb_up_sfa_sales.dhh_series_id" +
                    " AND tb_terminal_sell_history.is_gift = 0" +
                    " UNION ALL\n" +
                    //本地销售记录中减赠品
                    " SELECT DISTINCT\n" +
                    " 1 AS RdFlag,\n" +
                    " tb_up_sfa_sales.product_id_g,\n" +
                    " tb_product.product_name,\n" +
                    " tb_product.product_spec_g,\n" +
                    " tb_up_sfa_sales.batch_code_g,\n" +
                    " tb_up_sfa_sales.ba_quantity_con_g,\n" +
                    " tb_up_sfa_sales.idate,\n" +
                    " tb_up_sfa_sales.pda_type,\n" +
                    " tb_up_sfa_sales.terminal_id,\n" +
                    " tb_terminal_sell_history.dhh_series_id," +
                    " tb_terminal_sell_history.is_gift," +
                    " tb_terminal_sell_history.tc_id" +
                    " FROM\n" +
                    " tb_up_sfa_sales\n" +
                    " INNER JOIN tb_terminal_sell_history\n" +
                    " LEFT JOIN tb_product ON tb_up_sfa_sales.product_id_g = tb_product.id\n" +
                    " WHERE\n" +
                    " tb_up_sfa_sales.pda_type = 'DHHTH'\n" +
                    " AND tb_up_sfa_sales.idate > tb_terminal_sell_history.create_date" +
                    " AND tb_terminal_sell_history.tc_id = tb_up_sfa_sales.scheme_id" +
                    " AND tb_terminal_sell_history.dhh_series_id = tb_up_sfa_sales.dhh_series_id" +
                    " AND tb_terminal_sell_history.is_gift = 1";

    /*************************************************************************************/
    //创建一键装车销售量视图
    public static final String SQL_VIEW_PLAN_LOAD_CAR =
            "CREATE VIEW vw_product_plan_load_car_quantity AS " +
                    "SELECT\n" +
                    "\tproduct_id,\n" +
                    "\tsum(\n" +
                    "\t\tCASE\n" +
                    "\t\tWHEN detail.flag = 0 THEN\n" +
                    "\t\t\tdetail.ba_quantity_con\n" +
                    "\t\tELSE\n" +
                    "\t\t\t0 - detail.ba_quantity_con\n" +
                    "\t\tEND\n" +
                    "\t) AS quantity\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\t0 AS flag,\n" +
                    "\t\t\ttb_up_sfa_sales.product_id,\n" +
                    "\t\t\ttb_up_sfa_sales.idate,\n" +
                    "\t\t\ttb_up_sfa_sales.ba_quantity_con AS ba_quantity_con\n" +
                    "\t\tFROM\n" +
                    "\t\t\ttb_up_sfa_sales\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tpda_type IN (\n" +
                    "\t\t\t\t'DPXS',\n" +
                    "\t\t\t\t'TCXS',\n" +
                    "\t\t\t\t'HHC',\n" +
                    "\t\t\t\t'KQZS',\n" +
                    "\t\t\t\t'DHHXS',\n" +
                    "\t\t\t\t'RWFLSP',\n" +
                    "\t\t\t\t'CLFLSP',\n" +
                    "\t\t\t\t'JFDHSP'\n" +
                    "\t\t\t)\n" +
                    "\t\tAND idate > (\n" +
                    "\t\t\tSELECT\n" +
                    "\t\t\t\tdatetime('now', 'start of day')\n" +
                    "\t\t)\n" +
                    "\t\tUNION ALL\n" +
                    "\t\t\tSELECT\n" +
                    "\t\t\t\t0 AS flag,\n" +
                    "\t\t\t\ttb_up_sfa_sales.product_id,\n" +
                    "\t\t\t\ttb_up_sfa_sales.idate,\n" +
                    "\t\t\t\ttb_up_sfa_sales.ba_quantity_con_g AS ba_quantity_con\n" +
                    "\t\t\tFROM\n" +
                    "\t\t\t\ttb_up_sfa_sales\n" +
                    "\t\t\tWHERE\n" +
                    "\t\t\t\tpda_type IN (\n" +
                    "\t\t\t\t\t'DPXS',\n" +
                    "\t\t\t\t\t'TCXS',\n" +
                    "\t\t\t\t\t'HHC',\n" +
                    "\t\t\t\t\t'KQZS',\n" +
                    "\t\t\t\t\t'DHHXS',\n" +
                    "\t\t\t\t\t'RWFLSP',\n" +
                    "\t\t\t\t\t'CLFLSP',\n" +
                    "\t\t\t\t\t'JFDHSP'\n" +
                    "\t\t\t\t)\n" +
                    "\t\t\tAND idate > (\n" +
                    "\t\t\t\tSELECT\n" +
                    "\t\t\t\t\tdatetime('now', 'start of day')\n" +
                    "\t\t\t)\n" +
                    "\t\t\tUNION ALL\n" +
                    "\t\t\t\tSELECT\n" +
                    "\t\t\t\t\t1 AS flag,\n" +
                    "\t\t\t\t\ttb_up_sfa_sales.product_id,\n" +
                    "\t\t\t\t\ttb_up_sfa_sales.idate,\n" +
                    "\t\t\t\t\ttb_up_sfa_sales.ba_quantity_con AS ba_quantity_con\n" +
                    "\t\t\t\tFROM\n" +
                    "\t\t\t\t\ttb_up_sfa_sales\n" +
                    "\t\t\t\tWHERE\n" +
                    "\t\t\t\t\tpda_type = 'ZCJH'\n" +
                    "\t\t\t\tAND idate > (\n" +
                    "\t\t\t\t\tSELECT\n" +
                    "\t\t\t\t\t\tdatetime('now', 'start of day')\n" +
                    "\t\t\t\t)\n" +
                    "\t) detail\n" +
                    "GROUP BY\n" +
                    "\tdetail.product_id";


    /**
     * 验证码字符串
     */
    public static String RANDOMS = "1234567890poiuytrewqasdfghjklmnbvcxzZXCVBNMLKJHGFDSAQWERTYUIOP";
    /**********************
     * 本终端的临期预警是否处理
     *************************/
    public static String TEMPORARY_IS_PROCESS = "TEMPORARY_IS_PROCESS";
    public static String NO_PROCESS = "NO_PROCESS";
    public static String PROCESS_OK = "PROCESS_OK";
    /**********************
     * 本终端的物料回收是否处理
     *************************/
    public static String MATERIALRECOVERS_IS_PROCESS = "MATERIALRECOVERS_IS_PROCESS";
    public static String NO = "NO";
    public static String OK = "OK";
    /******
     * 价格方案类型
     ******/
    public static String TERMINAL_PRICR = "ZDJG";//终端价格
    public static String NORMAL_PRICE = "ZCJ";//正常价
    public static String BUY_GIFT_PRICE = "MZJ";//买赠价
    public static String STANDARD_PRICE = "BZJG";//标准价格
    /************************
     * 积分计算相关
     *************************/
    public static int PRODUCT_FLAG = 0;//按商品
    public static int MONEY_FLAG = 1;//按金额
    public static int COMPUTE_1 = 0;//方式1  阶梯式
    public static int COMPUTE_2 = 1;//方式2  最大数量式
    /**
     * 日历所用变量
     */
    public static String FIRST_SELECT_DATE = "-1";
    public static String SECOND_SELECT_DATE = "-1";
    //***********************************************创建仓库库存详情视图SQL到此结束********************************************/
    public static int SELECT_SUM = 1;


    //*******************************************创建仓库库存实际库存量视图SQL到此结束************************************************************/


    //******************************************创建车辆物料库存视图*****************************************/
    public static int WEEK_SELECT_SUM = 1;
    //******************************************创建车辆物料库存视图到此结束*****************************************/
    public static String currentDay = "-1";
    //订单模式已结算和未结算数据，因为都没有入库，所以不加入可用库存数量
    //            " UNION ALL\n" +
    //            " SELECT DISTINCT\n" +
    //            " tb_up_sfa_sales.rd_flag,\n" +
    //            " tb_material.material_name,\n" +
    //            " tb_up_sfa_sales.product_id,\n" +
    //            " tb_material.materia_spec_g,\n" +
    //            " tb_up_sfa_sales.ba_quantity_con,\n" +
    //            " tb_up_sfa_sales.prodate,\n" +
    //            " tb_up_sfa_sales.origin_id,\n" +
    //            " tb_up_sfa_sales.idate,\n" +
    //            " tb_up_sfa_sales.batch_code,\n" +
    //            " tb_up_sfa_sales.ware_house_id\n" +
    //            " FROM\n" +
    //            " tb_up_sfa_sales\n" +
    //            " INNER JOIN tb_material_stock\n" +
    //            " LEFT JOIN tb_material ON tb_up_sfa_sales.product_id = tb_material.id\n" +
    //            " WHERE\n" +
    //            " tb_up_sfa_sales.pda_type = 'WLHS'\n" +
    //            " AND tb_up_sfa_sales.osg_flag = 1\n" +
    //            " AND tb_up_sfa_sales.idate > tb_material_stock.update_time";
    //******************************************创建仓库物料库存详情视图到此结束*****************************************/
    public static String currentWeekDay = "-1";
    public static String WEEK_SELECT_DATE = "-1";
    public static Date weekdate = new Date();
    public static Date WEEK_SELECT_WEEK = weekdate;
    public static int WEEK_CLEARTYPE = -1;
    public static int VISIT_INT = -1;
    public static int PRODUCTSALE_INT = -1;
    public static int TODAYVISIT_INT = -1;
    public static int VISITARRVING_INT = -1;
    public static int TERMINALCITY = -1;
    public static int TERMINALWARNING = -1;
    public static int SALESOFGOODS = -1;
    public static String TERMINALCITTY = "null";
    /******************************************
     * 假数据
     ******************************************/
    public static ArrayList<String> warehouse = new ArrayList<>();
    public static ArrayList<String> allwarehouse = new ArrayList<>();
    public static ArrayList<String> dates = new ArrayList<>();
    public static ArrayList<String> reason = new ArrayList<>();
    public static ArrayList<String> product = new ArrayList<>();
    public static ArrayList<String> units = new ArrayList<>();
    public static ArrayList<String> price_plan = new ArrayList<>();
    public static ArrayList<String> gift_or_offers = new ArrayList<>();
    public static ArrayList<String> offers_info = new ArrayList<>();
    public static int ordertype = -1;

    static {
        warehouse.add("正品库");
        warehouse.add("临期库");
        allwarehouse.add("正品库");
        allwarehouse.add("临期库");
        allwarehouse.add("返货库");
        allwarehouse.add("退货库");
        dates.add("2016-5-3");
        dates.add("2016-1-3");
        dates.add("2016-6-6");
        reason.add("不想要了");
        reason.add("破损");
        reason.add("超过保质期");
        product.add("伊利有机奶");
        product.add("伊利脱脂奶");
        product.add("伊利纯牛奶");
        units.add("箱");
        units.add("排");
        units.add("组");
        price_plan.add("52.45/5.5");
        price_plan.add("12.0/1.2");
        price_plan.add("45/4.5");
        gift_or_offers.add("优惠");
        gift_or_offers.add("赠品");
        offers_info.add("伊利有机奶500ml优惠0.5元");
        offers_info.add("伊利有机奶500ml优惠0.5元");
        offers_info.add("伊利有机奶500ml优惠0.5元");
    }

    //报表 类型常数
    public static final int TodayVisit = 1;
    public static final int terminalsale = 2;
    public static final int terminalditribute = 3;
    public static final int itemsale = 4;
    public static final int updebt = 5;
    public static final int orderachieve = 6;
    public static final int adventwarning = 7;
    public static final int visiteachieve = 8;
    public static final int dueaccounts = 9;
    public static final int salegather = 10;
    public static final int Invoicinggather = 11;
    public static final int todaysale = 12;
    public static final int monthsale = 13;
}
