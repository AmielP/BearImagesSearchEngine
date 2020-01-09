package components.bearSearchAdvDataGrids.advancedSearch
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectAdvancedSearch;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	
	import interfaces.IRemotable;
	import interfaces.ISearch;
	
	public class AdvancedSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var iSearch:ISearch;
		
		private var acAdvancedSearch:ArrayCollection;
		// Erase if not needed
//		private var dpAdvancedSearch:ArrayCollection;
		
		public function AdvancedSearchAdvDataGridClass()
		{
			super();
			iSearch = new RemoteObjectWebSearch();
			addEventListener(FlexEvent.INITIALIZE, iSearch.initializeHandler);
			addEventListener(FlexEvent.CREATION_COMPLETE, iSearch.creationCompleteHandler);
		}
		
		public function search(advancedSearchQuery:String):void
		{
			advancedSearch(advancedSearchQuery, false);
		}
		
		private function advancedSearch(advancedSearchQuery:String, isWebSearch:Boolean):void
		{
			trace("Remote object in AdvancedSearchAdvDataGrid class: " + iSearch.remoteObject);
			var token:AsyncToken = iSearch.remoteObject.initiateWebSearch(advancedSearchQuery, isWebSearch);
			var responder:AsyncResponder = new AsyncResponder(advancedSearch_resultHandler, advancedSearch_faultHandler);
			token.addResponder(responder);
		}
		
		private function advancedSearch_resultHandler(event:ResultEvent, token:Object):void
		{
			acAdvancedSearch = new ArrayCollection();
			acAdvancedSearch = event.result as ArrayCollection;
			
			dataProvider = acAdvancedSearch;
			trace("Success: advancedSearch_resultHandler reached");
		}
		
		private function advancedSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			trace("advancedSearch_faultHandler: " + event);
			Alert.show("Failure: " + event, "advancedSearch_faultHandler", 4, this);
		}
	}
}