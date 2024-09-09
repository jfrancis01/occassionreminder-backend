package com.occassionreminder.constants;

public final class MyConstants {
	
	private MyConstants() {
		
	}
	
	public static enum OccassionType{
		Birthday, Confirmation, Wedding, Anniversary, Graduation;
	}
	
	public static enum Offset{
		dayoff{
			public String toString() {
				return "day off";
			}
		},
		daybefore{
			public String toString() {
				return "day before";
			}
		},
		weekbefore{
			public String toString() {
				return "week before";
			}
		}
	}

}
