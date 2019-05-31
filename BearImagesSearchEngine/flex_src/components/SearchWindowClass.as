package components
{
	import components.base.BearSearchBase;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWindow;
	
	import events.BearSearchEvent;
	import events.WebSearchQueryEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.RemoteObject;
	
	import spark.components.Window;
	
	public class SearchWindowClass extends Window
	{
		public var bearSearchBase:BearSearchBase = new BearSearchBase();
//		private var passTheSearchQuery:WebSearchQueryEvent;
		
//		[Bindable]
//		private var _webSearchQuery:String;
		
		public function SearchWindowClass()
		{
			super();
//			addEventListener(FlexEvent.INITIALIZE, initializeHandler);
//			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
		}
		
//		override public function initializeHandler(event:FlexEvent):void
//		{
//			amf = new AMFClass();
//			amf.initAMFClassChannel();
//		}
		
//		override public function creationCompleteHandler(event:FlexEvent):void
//		{
//			// The custom BearSearchBase component dispatches a "search"
//			// event when the web search query is submitted in the search box. Listen for this.
//			bearSearchBase.addEventListener(BearSearchEvent.SEARCH, searchHandler);
//			
//			
//		}
		
//		private function searchHandler(event:BearSearchEvent):void
//		{
//			trace("arrive at searchHandler");
//			passTheSearchQuery = new WebSearchQueryEvent(WebSearchQueryEvent.PASSWEBSEARCHQUERY);
//			passTheSearchQuery.webSearchQuery = _webSearchQuery;
//			dispatchEvent(passTheSearchQuery);
//		}
//		
//		override public function initRemoteObject():void
//		{
//			super.initRemoteObject();
//			
//		}
//		
//		private function webSearch():void
//		{
//			var token:AsyncToken = roBearImageData.
//		}
	}
}