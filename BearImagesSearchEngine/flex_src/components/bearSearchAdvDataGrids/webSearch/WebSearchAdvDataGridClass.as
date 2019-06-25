			package components.bearSearchAdvDataGrids.webSearch
{
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	
	import data.BingWebSearchResultVO;
	
	import interfaces.ISearch;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.utils.StringUtil;
	
	import spark.skins.spark.windowChrome.CloseButtonSkin;
	
	public class WebSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var iSearch:ISearch;
		
//		[Bindable]
//		private var webSearchDataRecord:ArrayCollection;
		
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

		private function webSearch_resultHandler(event:ResultEvent, token:Object):void
		{
			var acWebSearch:ArrayCollection = new ArrayCollection();
			acWebSearch = event.result as ArrayCollection;
			dataProvider = acWebSearch;
			trace("Success: webSearch_resultHandler reached");
			
			// Must change from acWebSearch.length to 150 since the loop will not stop otherwise.
			for(var i:int = 0; i < 150; i++)
			{
				var bingWebSearchResultData:BingWebSearchResultVO = new BingWebSearchResultVO();
				
//				bingWebSearchResultData.contentID = acWebSearch[i].contentID;
				bingWebSearchResultData.contentUrl = acWebSearch[i].contentUrl;
				bingWebSearchResultData.name = acWebSearch[i].name;
//				bingWebSearchResultData.imageData = acWebSearch[i].imageData;
				
				acWebSearch.addItem(bingWebSearchResultData);
			}
			dropResultsTable();
			createResultsDataTable();
//			upsertResultsData(acWebSearch);
			insertResultsData(acWebSearch);
//			updateResultsData(acWebSearch);
//			insertAndUpdateResultsData(acWebSearch);
			selectResultsData();
		}
		
		private function webSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			trace("webSearch_faultHandler: " + event);
			Alert.show("Failure: " + event, "webSearch_faultHandler", 4, this);
		}
		
		private function createResultsDataTable():void
		{
			var token:AsyncToken = iSearch.remoteObject.createWebResultsTable();
			var responder:AsyncResponder = new AsyncResponder(createResultsDataTable_resultHandler, createResultsDataTable_faultHandler);
			token.addResponder(responder);
		}
		
		private function createResultsDataTable_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("createResultsDataTable_resultHandler reached", "SUCCESS", 4, this);
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function createResultsDataTable_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Create Results Data Table", 4, this);
		}
		
		public function selectResultsData():void
		{
			var token:AsyncToken = iSearch.remoteObject.selectWebResultsData();
			var responder:AsyncResponder = new AsyncResponder(selectResultsData_resultHandler, selectResultsData_faultHandler);
			token.addResponder(responder);
		}
		
		private function selectResultsData_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("selectResultsData_resultHandler reached", "SUCCESS", 4, this);
			var resultIDList:ArrayCollection = event.result as ArrayCollection;
			dataProvider = resultIDList;
			dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function selectResultsData_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Select Results Data", 4, this);
		}
		
		private function insertResultsData(data:ArrayCollection):void
		{
			var token:AsyncToken = iSearch.remoteObject.insertWebResultsData(data);
			var responder:AsyncResponder = new AsyncResponder(insertResultsData_resultHandler, insertResultsData_faultHandler);
			token.addResponder(responder);
		}
		
		private function insertResultsData_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("insertResultsData_resultHandler reached", "SUCCESS", 4, this);
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function insertResultsData_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Insert Results Data", 4, this);
		}
		
		private function updateResultsData(data:ArrayCollection):void
		{
			var token:AsyncToken = iSearch.remoteObject.updateWebResultsData(data);
			var responder:AsyncResponder = new AsyncResponder(updateResultsData_resultHandler, updateResultsData_faultHandler);
			token.addResponder(responder);
		}
		
		private function updateResultsData_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("updateResultsData_resultHandler reached", "SUCCESS", 4, this); 
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function updateResultsData_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Update Results Data", 4, this);
		}
		
		private function insertAndUpdateResultsData(data:ArrayCollection):void
		{
			var token:AsyncToken = iSearch.remoteObject.insertAndUpdateWebResultsData(data, true);
			var responder:AsyncResponder = new AsyncResponder(insertAndUpdateResultsData_resultHandler, insertAndUpdateResultsData_faultHandler);
			token.addResponder(responder);
		}
		
		private function insertAndUpdateResultsData_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("insertAndUpdateResultsData_resultHandler reached", "SUCCESS", 4, this);
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function insertAndUpdateResultsData_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT = Insert and Update Results Data", 4, this);			
		}
		
		//Ask about what to do with create table and insert data into table functions?
		private function upsertResultsData(data:ArrayCollection):void
		{
			var token:AsyncToken = iSearch.remoteObject.upsertWebResultsData(data);
			var responder:AsyncResponder = new AsyncResponder(upsertResultsData_resultHandler, upsertResultsData_faultHandler);
			token.addResponder(responder);
		}
		
		private function upsertResultsData_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("upsertResultsData_resultHandler reached", "SUCCESS", 4, this);
			//ask about what goes in result handler for insert
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function upsertResultsData_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Upsert Results Data", 4, this);
		}
		
		private function deleteResultsData():void
		{
			var token:AsyncToken = iSearch.remoteObject.deleteWebResultsData();
			var responder:AsyncResponder = new AsyncResponder(deleteResultsData_resultHandler, deleteResultsData_faultHandler);
			token.addResponder(responder);
		}
		
		private function deleteResultsData_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("deleteResultsData_resultHandler reached", "SUCCESS", 4, this);
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function deleteResultsData_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Delete Results Data", 4, this);
		}
		
		private function dropResultsTable():void
		{
			var token:AsyncToken = iSearch.remoteObject.dropTable();
			var responder:AsyncResponder = new AsyncResponder(dropResultsTable_resultHandler, dropResultsTable_faultHandler);
			token.addResponder(responder);
		}
		
		private function dropResultsTable_resultHandler(event:ResultEvent, token:Object):void
		{
			Alert.show("dropResultsTable_resultHandler reached", "SUCCESS", 4, this);
			this.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		private function dropResultsTable_faultHandler(event:FaultEvent, token:Object):void
		{
			Alert.show(event.fault.faultCode + "\n" + event.fault.faultString + "\n" + event.fault.faultDetail, "FAULT - Drop Results Table", 4, this);
		}
	}
}