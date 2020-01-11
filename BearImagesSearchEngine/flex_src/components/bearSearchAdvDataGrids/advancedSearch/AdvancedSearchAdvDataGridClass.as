package components.bearSearchAdvDataGrids.advancedSearch
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.mx_internal;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectAdvancedSearch;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectWebSearch;
	import components.bearSearchAdvDataGrids.webSearch.WebSearchAdvDataGrid;
	
	import interfaces.IRemotable;
	import interfaces.ISearch;
	
	public class AdvancedSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var iSearch:ISearch;
		private var _searchingAlert:Alert;
		
		private var _acAdvancedSearch:ArrayCollection;
		
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
			_searchingAlert = Alert.show("This may take a few minutes. Please wait.", "Searching for Images (Bear Filter)", 4, this);
			_searchingAlert.mx_internal::alertForm.removeChild(_searchingAlert.mx_internal::alertForm.mx_internal::buttons[0]);
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
			_acAdvancedSearch = new ArrayCollection();
			_acAdvancedSearch = event.result as ArrayCollection;
			
			dataProvider = _acAdvancedSearch;
			this.dispatchEvent(event);
			trace("Success: advancedSearch_resultHandler reached");
		}
		
		private function advancedSearch_faultHandler(event:FaultEvent, token:Object):void
		{
			PopUpManager.removePopUp(_searchingAlert);
			trace("advancedSearch_faultHandler: " + event);
			Alert.show("Failure: " + event, "advancedSearch_faultHandler", 4, this);
		}
		
		public function set acAdvancedSearch(_acAdvancedSearch:ArrayCollection):void
		{
			this._acAdvancedSearch = _acAdvancedSearch;
		}
		
		public function get acAdvancedSearch():ArrayCollection
		{
			return _acAdvancedSearch;
		}
		
		public function set searchingAlert(_searchingAlert:Alert):void
		{
			this._searchingAlert = _searchingAlert;
		}
		
		public function get searchingAlert():Alert
		{
			return _searchingAlert;
		}
	}
}