package data
{
	import air.desktop.URLFilePromise;
	
	import spark.components.Image;

	[Bindable]
	[RemoteClass(alias="webSearch.BingWebSearchVO")]
	public class BingWebSearchVO
	{
		private var _bearImageID:int;
		private var _webSearchURL:String;
		private var _name:String;
		private var _thumbnailURL:String;
		private var _datePublished:Date;
		private var _isFamilyFriendly:Boolean;
		private var _contentURL:Image;
		private var _hostPageURL:String;
		private var _contentSize:String;
		private var _encodingFormat:String;
		private var _hostPageDisplayURL:String;
		private var _width:int;
		private var _height:int;
		private var _thumbnail:String;
		
		public function BingWebSearchVO()
		{
		}
		
		
		public function get bearImageID():int
		{
			return _bearImageID;
		}

		public function set bearImageID(value:int):void
		{
			_bearImageID = value;
		}

		public function get webSearchURL():String
		{
			return _webSearchURL;
		}

		public function set webSearchURL(value:String):void
		{
			_webSearchURL = value;
		}

		public function get name():String
		{
			return _name;
		}

		public function set name(value:String):void
		{
			_name = value;
		}

		public function get thumbnailURL():String
		{
			return _thumbnailURL;
		}

		public function set thumbnailURL(value:String):void
		{
			_thumbnailURL = value;
		}

		public function get datePublished():Date
		{
			return _datePublished;
		}

		public function set datePublished(value:Date):void
		{
			_datePublished = value;
		}

		public function get isFamilyFriendly():Boolean
		{
			return _isFamilyFriendly;
		}

		public function set isFamilyFriendly(value:Boolean):void
		{
			_isFamilyFriendly = value;
		}

		public function get contentURL():Image
		{
			return _contentURL;
		}

		public function set contentURL(value:Image):void
		{
			_contentURL = value;
		}

		public function get hostPageURL():String
		{
			return _hostPageURL;
		}

		public function set hostPageURL(value:String):void
		{
			_hostPageURL = value;
		}

		public function get contentSize():String
		{
			return _contentSize;
		}

		public function set contentSize(value:String):void
		{
			_contentSize = value;
		}

		public function get encodingFormat():String
		{
			return _encodingFormat;
		}

		public function set encodingFormat(value:String):void
		{
			_encodingFormat = value;
		}

		public function get hostPageDisplayURL():String
		{
			return _hostPageDisplayURL;
		}

		public function set hostPageDisplayURL(value:String):void
		{
			_hostPageDisplayURL = value;
		}

		public function get width():int
		{
			return _width;
		}

		public function set width(value:int):void
		{
			_width = value;
		}

		public function get height():int
		{
			return _height;
		}

		public function set height(value:int):void
		{
			_height = value;
		}

		public function get thumbnail():String
		{
			return _thumbnail;
		}

		public function set thumbnail(value:String):void
		{
			_thumbnail = value;
		}


	}
}