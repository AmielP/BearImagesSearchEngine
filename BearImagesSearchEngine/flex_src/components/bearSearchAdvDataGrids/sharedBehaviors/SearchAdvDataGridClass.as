package components.bearSearchAdvDataGrids.sharedBehaviors
{
	import interfaces.IFormattable;
	import interfaces.IRemotable;
	import interfaces.IRemoteObject;
	
	import mx.collections.ArrayCollection;
	import mx.controls.AdvancedDataGrid;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class SearchAdvDataGridClass extends AdvancedDataGrid
	{
		private var _roBearImageData:IRemoteObject;
		
		private var _dpSearch:ArrayCollection;
		
		public function SearchAdvDataGridClass()
		{
			super();
		}
		
		protected function get roBearImageData():IRemoteObject
		{
			return _roBearImageData;
		}
		
		protected function set roBearImageData(value:IRemoteObject):void
		{
			_roBearImageData = value;
		}

		protected function get dpSearch():ArrayCollection
		{
			return _dpSearch;
		}

		protected function set dpSearch(value:ArrayCollection):void
		{
			_dpSearch = value;
		}
	}
}