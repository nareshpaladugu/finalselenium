package com.broward.utilities;
	
	@SuppressWarnings("serial")
	public class TestFailedException extends Exception	
	{
		
		String message="Test Failed";
		public TestFailedException(String message){
			this.message=message;
		}
		public String getMessage(){
			return message;
		}
	}

