package
{
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;

	public class AMFClass
	{
		private var amfUrl:String = "http://localhost:8080/BearImagesSearchEngine/messagebroker/amf";
		
		[Bindable]
		public var amfClassChannelSet:ChannelSet = null; //the remote object references this in their channelSet property
		private static var amfChannel:AMFChannel = null;
		
		public function AMFClass()
		{
			
		}
		
		public function initAMFClassChannel():void
		{
			amfChannel = new AMFChannel('my-amf', amfUrl);
			amfClassChannelSet = new ChannelSet();
			amfClassChannelSet.addChannel(amfChannel);
			amfChannel.connectTimeout = 500;
			amfChannel.requestTimeout = 500;
		}
		
		public function getAMFUrl():String
		{
			return amfUrl; 
		}
	}
}