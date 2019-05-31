package components.base
{
	import components.bearSearchAdvDataGrids.advancedSearch.AdvancedSearchAdvDataGrid;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectSearch;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	import components.bearSearchAdvDataGrids.webSearch.WebSearchAdvDataGrid;
	
	import events.BearSearchEvent;
	
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.ui.Keyboard;
	
	import interfaces.IFormattable;
	import interfaces.IHandler;
	import interfaces.IRemotable;
	import interfaces.ISearch;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.StringUtil;
	
	import spark.components.Button;
	import spark.components.Group;
	import spark.components.TextInput;
	import spark.effects.Move;
	
	//delete all the state change stuff whenever possible
	public class BearSearchBaseClass extends Group
	{
		// MXML Components must be declared public
		// This is a current limitation in this 
		// version of flex and may change in the
		// future.
		public var adgWebSearch:WebSearchAdvDataGrid = new WebSearchAdvDataGrid();
		public var adgAdvancedSearch:AdvancedSearchAdvDataGrid = new AdvancedSearchAdvDataGrid();
		
		[Bindable]
//		public var tiInitialSearch:TextInput = new TextInput();
		public var tiSearch:TextInput = new TextInput();
//		public var bInitialSearch:Button = new Button();
		public var bSearch:Button = new Button();
		// Bindable button object in order to sustain the effects
		[Bindable]
		public var bPreviousState:Button = new Button();
		
		private var iSearch:ISearch/* = new RemoteObjectWebSearch()*/;
		
//		[Bindable]
//		public var dpBingWebSearch:ArrayCollection;
		
		public function BearSearchBaseClass()
		{
			super();
//			adgWebSearch = new WebSearchAdvDataGrid();
//			adgAdvancedSearch = new AdvancedSearchAdvDataGrid();
//			tiSearch = new TextInput();
//			bSearch = new Button();
//			bPreviousState = new Button();
			iSearch = new RemoteObjectWebSearch();
			addEventListener(FlexEvent.INITIALIZE, iSearch.initializeHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, iSearch.creationCompleteHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
//			trace("iSearch.initializeHandler: " + iSearch.initializeHandler + "\n" + "iSearch.creationCompleteHandler: " + iSearch.creationCompleteHandler + "\n" + "creationCompleteHandler: " + creationCompleteHandler);
		}
		
		// Creation complete is a good time to add event listeners
		// and interact with child components.
		public function creationCompleteHandler(event:FlexEvent):void
		{
//			tiInitialSearch.addEventListener(FlexEvent.ENTER, tiInitialSearch_enterHandler);
			bSearch.addEventListener(MouseEvent.CLICK, bSearch_clickHandler);
			tiSearch.addEventListener(FlexEvent.ENTER, tiSearch_enterHandler);
//			bInitialSearch.addEventListener(MouseEvent.CLICK, bInitialSearch_clickHandler);
			
			bPreviousState.addEventListener(MouseEvent.CLICK, bpreviousState_clickHandler);
//			parentApplication.addEventListener(BearSearchEvent.DATA_SEARCH_CONFIRMED, dataSearchConfirmedHandler);
			
		}
		
//		private function reportKeyDown(event:KeyboardEvent):void
//		{
//			stage.addEventListener(KeyboardEvent.KEY_DOWN, reportKeyDownHandler);
//		}
//		
//		private function reportKeyUp(event:KeyboardEvent):void
//		{
//			stage.removeEventListener(KeyboardEvent.KEY_DOWN, reportKeyDownHandler);
//		}
		
//		private function tiInitialSearch_enterHandler(event:FlexEvent):void
//		{
//				trace("HIT ME!");
//				bInitialSearch_clickHandler(event as MouseEvent);
//		}
		
		// Continue this effect later
		private function moveElements():void
		{
			var changeToNextPageEffect:Move = new Move();
			changeToNextPageEffect.xTo = 5;
			changeToNextPageEffect.yTo = 10;
		}
		
		private function tiSearch_enterHandler(event:FlexEvent):void
		{
			trace("Hit me!");
			bSearch_clickHandler(event as MouseEvent);
		}
		
//		private function bSearch_clickHandler(event:MouseEvent):void
//		{
//			var bearSearchEvent:BearSearchEvent = new BearSearchEvent(BearSearchEvent.SEARCH);
//			bearSearchEvent.webSearchQuery = tiSearch;
//			promptEmptyWebSearchQueryError(bearSearchEvent);
//			
//			
//			
//			// Dispatch an event to signal that the query has been submitted
//			// This goes to the top
//			dispatchEvent(bearSearchEvent);
//		}
		
		private function bSearch_clickHandler(event:MouseEvent):void
		{
//			var searchEvent:BearSearchEvent = new BearSearchEvent(BearSearchEvent.SEARCH);
//			searchEvent.webSearchQuery = tiSearch;
//			dispatchEvent(searchEvent);
			
			
			// uncomment back if not working
			webSearch();
			
//			var bearSearchEvent:BearSearchEvent = new BearSearchEvent(BearSearchEvent.SEARCH);
//			bearSearchEvent.webSearchQuery = tiSearch;
//			promptEmptyWebSearchQueryError(bearSearchEvent);
//			
//			// Dispatch an event to signal that the query has been submitted
//			// This goes to the top
//			dispatchEvent(bearSearchEvent);
		}
		
		private function bpreviousState_clickHandler(event:MouseEvent):void{};
		
		private function dataSearchConfirmedHandler(event:BearSearchEvent):void
		{
			bSearch_clickHandler(null);
		}
		
		private function webSearch():void
		{
			trace("iSearch in bSearch_clickHandler: " + iSearch);
			trace("Remote object in bSearch_clickHandler: " + iSearch.remoteObject);
			adgWebSearch.webSearch(tiSearch.text, iSearch.remoteObject);
		}
		
		private function webSearch_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("SUCCESS: FIRST IMAGE WOOP");
		}
		
		private function webSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			adgWebSearch.webSearch_faultHandler(event, token);
		}
		
//		private function webSearch_resultHandler(event:ResultEvent, token:Object):void
//		{
//			dpBingWebSearch = new ArrayCollection();
//			dpBingWebSearch = event.result as ArrayCollection;
//			adgWebSearch.dataProvider = dpBingWebSearch as ICollectionView;
//			Alert.show("Success: first image is " + dpBingWebSearch[0].contentUrl, "webSearch_resultHandler", 4, this);
//			trace("Success: Button Click Reached");
//		}
//		
//		private function webSearch_faultHandler(event:FaultEvent, token:Object):void
//		{
//			trace("webSearch_faultHandler: " + event);
//			Alert.show("Failure: " + event, "webSearch_faultHandler", 4, this);
//		}
//		
//		private function webSearch():void
//		{
//			if (!StringUtil.trim(tiSearch.text))
//			{
//				tiSearch.text = "bear";
//			}
//			
//			// Be sure that the remote object in iSearch is not null.
//			trace("remote object in bearSearchBase class: " + iSearch.remoteObject);
//			var token:AsyncToken = iSearch.remoteObject.initiateWebSearch(tiSearch.text);
//			var responder:AsyncResponder = new AsyncResponder(webSearch_resultHandler, webSearch_faultHandler, token);
//			token.addResponder(responder);
//		}
//		
//		private function promptEmptyWebSearchQueryError(event:BearSearchEvent):void
//		{
//			if (event.webSearchQuery.text.length == 0)
//			{
//				Alert.show("Search entry cannot be empty", "Error - Empty Web Search Query", 4, this);
//				return;
//			}
//		}
	}
}