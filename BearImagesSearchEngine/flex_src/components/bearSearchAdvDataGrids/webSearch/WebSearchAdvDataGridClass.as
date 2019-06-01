package components.bearSearchAdvDataGrids.webSearch
{
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	
	import interfaces.ISearch;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.utils.StringUtil;
	
	public class WebSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var iSearch:ISearch;
		
		[Bindable]
		private var webSearchData:ArrayCollection;
		
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
			webSearch(webSearchQuery);
		}
		
		// Make these following methods private in a parent search class and only allow the abstract method to access these private methods in order to encapsuiate their behavior
		private function webSearch(webSearchQuery:String):void
		{
//			if (!StringUtil.trim(webSearchQuery))
//			{
//				webSearchQuery = "bear";
//			}
			trace("Remote object in WebSearchAdvDataGrid class: " + iSearch.remoteObject);
			var token:AsyncToken = iSearch.remoteObject.initiateWebSearch(webSearchQuery);
			var responder:AsyncResponder = new AsyncResponder(webSearch_resultHandler, webSearch_faultHandler, token);
			token.addResponder(responder);
		}

		public function webSearch_resultHandler(event:ResultEvent, token:Object):void
		{
			webSearchData = new ArrayCollection();
			webSearchData = event.result as ArrayCollection;
			dataProvider = webSearchData;
			trace("Success: webSearch_resultHandler reached");
		}
		
		public function webSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			trace("webSearch_faultHandler: " + event);
			Alert.show("Failure: " + event, "webSearch_faultHandler", 4, this);
		}
	}
}