package interfaces
{
	import mx.rpc.remoting.RemoteObject;

	public interface IRemotable
	{
		function get remoteObject():RemoteObject;
		function set remoteObject(value:RemoteObject):void;
	}
}