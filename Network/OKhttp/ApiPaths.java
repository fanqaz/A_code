package cn.com.geekplus.app.rf.api;

public class ApiPaths {
    /**仓库列表*/
    public static final String WAREHOUSE = "/beetle/api_v1/warehouse/getAllWarehouseByCode";
    /**登录*/
    public static final String LOGIN = "/beetle/api_v1/user/login";
    /**上架*/
    public static final String ONSHELF = "/beetle/api_v1/replenishRF/selectUnfinishedList";

    public static final String SYSPARAMQUERY = "/beetle/api_v1/sysparam/query?group=base&paramKey=auto_pick_task";
    /**上架确认*/
    public static final String ONSHELFCONFIRM = "/beetle/api_v1/replenishRF/selectUnfinishedBycondition";
    /**上架任务--通过商品条码查询相关信息*/
    public static final String ONSHELFTASK= "/beetle/api_v1/replenishRF/selectSkuInfo";
    /**下架-拣货类型*/
    public static final String SEARCH_PICK_TYPE= "/beetle/api_v1/pickwork/searchPickType";
    /**下架-未完成数量*/
    public static final String SEARCH_PICK_COUNT= "/beetle/api_v1/pickwork/picklist_search_unfinishCount";
    /**上架任务 --确认货架是否正确*/
    public static final String ONSHELFTASKCHECKSHELF= "/beetle/api_v1/replenishRF/checkShelfBin";
    /**上架任务--确认*/
    public static final String ONSHELFTASKCONFIRM= "/beetle/api_v1/replenishRF/startRepl";

    /**下架-获取拣选任务*/
    public static final String GET_PICK_TASK= "/beetle/api_v1/pickwork/picklist_search";

    /**下架-根据拣选单号获取任务*/
    public static final String QUERY_PICK_TASK= "/beetle/api_v1/pickwork/picklist_query";
    /**下架-根据容器获取任务*/
    public static final String QUERY_PICK_CONTAINER= "/beetle/api_v1/pickwork/picklistQuickSearch";

    public static final String QUERY_ORDER_TYPES= "/beetle/api_v1/dict/queryDetails?objectCode=wave_type";
    /**下架-作业方式*/
    public static final String QUERY_PICK_WAY= "/beetle/api_v1/dict/queryDetails?objectCode=pick_way";
    /**下架-SKU编码验证*/
    public static final String PICK_CHECK_SKU_CODE= "/beetle/api_v1/pickwork/checkSkuCodeValidity";
    /**下架-领取拣选任务*/
    public static final String FETCH_PICK_LIST = "/beetle/api_v1/pickwork/picklist_fetch";
    /**下架-拣选确认-开始执行*/
    public static final String PICK_TASK_EXECUTE = "/beetle/api_v1/pickwork/picktask_execute";
    /**下架-获取token*/
    public static final String PICK_GET_TOKEN = "/beetle/token/get";
    /**下架-查询拣货单所有sku*/
    public static final String PICK_LIST_ALL_ITEM = "/beetle/api_v1/pickwork/picklist_search_allitem";

    /**补货下架-获取拣选任务*/
    public static final String REPLENISH_GET_PICK_TASK = "/beetle/api_v1/restock/restockRF/picklist_fetch";
    public static final String REPLENISH_GET_PICK_TASK_AUTO = "/beetle/api_v1/restock/restockRF/picklist_auto_fetch";
    /**补货下架-未完成数量*/
    public static final String GET_PICK_COUNT_REPLENISH = "/beetle/api_v1/restock/restockRF/picklist_search_unfinishCount";
    /**补货下架-拣选任务绑定容器*/
    public static final String REPLENISH_BIND_CONTAINER = "/beetle/api_v1/restock/restockRF/picklist_container_bind";
    /**补货下架-搜索拣选任务*/
    public static final String REPLENISH_SEARCH_PICK_TASK = "/beetle/api_v1/restock/restockRF/picklist_search";
    /**补货下架-验证sku信息*/
    public static final String REPLENISH_SELECT_SKU_INFO = "/beetle/api_v1/restock/restockRF/select_sku_info";
    /**补货下架-验证sku信息*/
    public static final String REPLENISH_EXECUTE_PICK_TASK = "/beetle/api_v1/restock/restockRF/picktask_execute";


    /**上架任务--获取token*/
    public static final String ONSHELFTASKTOKEN= "/beetle/token/get";
    /**获取包裹承运商列表*/
    public static final String PACKAGECARRIER= "/beetle/api_v1/carrierInfo/queryAllCarrier?carrierInfo=";
    /**获取包裹承运商详细信息*/
    public static final String PACKAGECARRIERDETAIL= "/beetle/api_v1/express/transferTask/carrierSum?carrierCode=";
    /**校验容器号*/
    public static final String PACKAGESCANCONTAINER= "/beetle/api_v1/express/transferTask/scanContainer";
    /**包裹扫描--确认*/
    public static final String PACKAGESCANCONFIRM= "/beetle/api_v1/express/transferTask/confirm";
    /**包裹扫描--满箱*/
    public static final String PACKAGESCANFULLCONTAINER= "/beetle/api_v1/express/transferTask/fullContainer";
    /**订单包裹查询--已扫描*/
    public static final String PACKAGEORDERLISTSCANNED= "/beetle/api_v1/express/transferTask/pageListCurrentContainer?pageSize=";
    /**订单包裹查询--未扫描*/
    public static final String PACKAGEORDERLIST= "/beetle/api_v1/express/transferTask/pageListOrder?pageSize=";
    /**订单包裹查询--未扫描--信息*/
    public static final String PACKAGEORDERINFO= "/beetle/api_v1/express/transferTask/carrierScanInfo?type=";
    /**订单包裹查询--满箱容器*/
    public static final String PACKAGEFULLCONTAINER= "/beetle/api_v1/express/transferTask/carrierFullContainerInfo?carrierCode=";
    /**订单包裹查询--满箱容器--查询*/
    public static final String PACKAGEFULLCONTAINERSEARCH= "/beetle/api_v1/express/transferTask/pageListFullContainer";
    /**订单包裹查询--拦截*/
    public static final String PACKAGELISTINTERCEPT= "/beetle/api_v1/express/transferTask/pageListInterceptPkg";
    /**订单包裹查询--删除包裹*/
    public static final String PACKAGEDELETEPKG = "/beetle/api_v1/express/transferTask/deletePkg";
    /**订单包裹查询--拦截--满箱*/
    public static final String PACKAGEFULLCONTAINERINTERCEPT = "/beetle/api_v1/express/transferTask/fullContainerIntercept";
    /**订单包裹查询--取消包裹--容器检查*/
    public static final String PACKAGECANCELCONTAINERINTERCEPT= "/beetle/api_v1/express/transferTask/scanContainerIntercept";
    /**订单包裹查询--取消包裹--确定*/
    public static final String PACKAGECANCELCONFIRMINTERCEPT= "/beetle/api_v1/express/transferTask/confirmIntercept";
    /**订单包裹查询--取消包裹--满箱*/
    public static final String PACKAGECANCELFULLCONTAINERINTERCEPT = "/beetle/api_v1/express/transferTask/fullContainerIntercept";

    public static final String STOCK_QUERY_BATCH_SKU = "/beetle/api_v2/inventory/queryByBatchAndSkuBin";
    /**上架--送货架*/
    public static final String DELIVERYRACK = "/beetle/api_v1/replenishRF/replReturnShelf";



}
