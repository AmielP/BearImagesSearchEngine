package components.bearSearchAdvDataGrids.sharedBehaviors
{
	import interfaces.IFormattable;
	import interfaces.IHandler;
	import interfaces.IRemotable;
	import interfaces.IRemoteObject;
	
	import mx.collections.ArrayCollection;
	import mx.controls.AdvancedDataGrid;
	import mx.events.FlexEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class SearchAdvDataGridClass extends AdvancedDataGrid
	{
		private var _remotable:IRemotable; // FlyBehavior flyBehavior
		private var _remoteObject:IRemoteObject; //SwimBehavior swimBehavior
		private var _formattable:IFormattable; // QuackBehavior quackBehavior
		
		private var _dpSearch:ArrayCollection;
		
		public function performFly():void
		{
			_remotable.remoteObject;
		}
		
		public function SearchAdvDataGridClass()
		{
			super();
		}
		
		public function get remotable():IRemotable // getFlyBehavior()
		{
			return _remotable; // return flyBehavior;
		}
		
		public function set remotable(value:IRemotable):void //setFlyBehavior(flyBehavior)
		{
			_remotable = value; // this.flyBehavior = flyBehavior;
		}
		
		protected function get remoteObject():IRemoteObject // getSwimBehavior()
		{
			return _remoteObject; // return swimBehavior;
		}
		
		protected function set remoteObject(value:IRemoteObject):void // setSwimBehavior(swimBehavior)
		{
			_remoteObject = value; // this.swimBehavior = swimBehavior;
		}
		
		protected function get formattable():IFormattable // getQuackBehavior()
		{
			return _formattable; // return quackBehavior;
		}
		
		protected function set formattable(value:IFormattable):void // setQuackBehavior(quackBehavior)
		{
			_formattable = value; // this.quackBehavior = quackBehavior;
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