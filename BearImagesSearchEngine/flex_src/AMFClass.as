package
{
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	
	public class AMFClass
	{
		private var amfURL:String = "http://localhost:8080/BearImagesSearchEngine/messagebroker/amf";
		
		[Bindable]
		public var amfClassChannelSet:ChannelSet = null; //Remote object reference
		private static var amfClassChannel:AMFChannel = null;
		
		public function AMFClass()
		{
			
		}
		
		public function initAMFClassChannel():void
		{
			amfClassChannel = new AMFChannel('my-amf', amfURL);
			amfClassChannelSet = new ChannelSet();
			amfClassChannelSet.addChannel(amfClassChannel);
		}
		
		public function get getAMFURL():String
		{
			return amfURL;
		}
	}
}