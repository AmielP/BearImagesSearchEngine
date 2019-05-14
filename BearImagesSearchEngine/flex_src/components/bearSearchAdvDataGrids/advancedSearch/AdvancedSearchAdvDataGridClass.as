package components.bearSearchAdvDataGrids.advancedSearch
{
	import components.bearSearchAdvDataGrids.sharedBehaviors.SearchAdvDataGridClass;
	import components.bearSearchAdvDataGrids.utilities.RemoteObjectAdvancedSearch;
	
	import interfaces.IRemotable;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class AdvancedSearchAdvDataGridClass extends SearchAdvDataGridClass
	{	
		private var dpAdvancedSearch:ArrayCollection;
		
		public function AdvancedSearchAdvDataGridClass()
		{
			super();
			roBearImageData = new RemoteObjectAdvancedSearch();
		}
	}
}