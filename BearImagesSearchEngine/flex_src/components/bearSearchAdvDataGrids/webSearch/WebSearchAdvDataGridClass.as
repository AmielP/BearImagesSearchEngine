package components.bearSearchAdvDataGrids.webSearch
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.mx_internal;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.Fault;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.utils.StringUtil;
	
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	
	import data.BingWebSearchResultVO;
	
	import interfaces.ISearch;
	
	public class WebSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var iSearch:ISearch;
		private var _searchingAlert:Alert;
		
		[Bindable]
		private var _acWebSearch:ArrayCollection;
		
		public function WebSearchAdvDataGridClass()
		{
			super();
			iSearch = new RemoteObjectWebSearch();
			addEventListener(FlexEvent.INITIALIZE, iSearch.initializeHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, iSearch.creationCompleteHandler);
		}
		
		// Make this an abstract method from a parent search class
		public function search(webSearchQuery:String):void
		{
			_searchingAlert = Alert.show("This may take a few minutes. Please wait.", "Searching for Images (No Filter)", 4, this);
			_searchingAlert.mx_internal::alertForm.removeChild(_searchingAlert.mx_internal::alertForm.mx_internal::buttons[0]);
			webSearch(webSearchQuery, true);
		}
		
		// Make these following methods private in a parent search class and only allow the abstract method to access these private methods in order to encapsuiate their behavior
		private function webSearch(webSearchQuery:String, isWebSearch:Boolean):Object
		{
//			if (!StringUtil.trim(webSearchQuery))
//			{
//				webSearchQuery = "bear";
//			}
			_acWebSearch = null;
			dataProvider = _acWebSearch;
			trace("Remote object in WebSearchAdvDataGrid class: " + iSearch.remoteObject);
			var token:AsyncToken = iSearch.remoteObject.initiateWebSearch(webSearchQuery, isWebSearch);
			var responder:AsyncResponder = new AsyncResponder(webSearch_resultHandler, webSearch_faultHandler, token);
			token.addResponder(responder);
			
			return token;
		}

		// Don't need for loop since results come back in through the dataProvider from the filtered table
		private function webSearch_resultHandler(event:ResultEvent, token:Object):void
		{
			_acWebSearch = new ArrayCollection();
			_acWebSearch = event.result as ArrayCollection;
			
			
//			for (var i:int = 0; i < 50; i++)
//			{
//				var bingWebSearchResultData:BingWebSearchResultVO = new BingWebSearchResultVO();
//				
//				bingWebSearchResultData.name = acWebSearch[i].name;
//				bingWebSearchResultData.contentUrl = acWebSearch[i].contentUrl;
//				
//				acWebSearch.addItem(bingWebSearchResultData);
//			}
			this.dispatchEvent(event);
//			dataProvider = acWebSearch;
			trace("Success: webSearch_resultHandler reached");
		}
		
		private function webSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			PopUpManager.removePopUp(_searchingAlert);
			trace("webSearch_faultHandler: " + event);
			Alert.show("Failure: " + event, "webSearch_faultHandler", 4, this);
		}
		
		public function get acWebSearch():ArrayCollection
		{
			return _acWebSearch;
		}
		
		public function get searchingAlert():Alert
		{
			return _searchingAlert;
		}
	}
}