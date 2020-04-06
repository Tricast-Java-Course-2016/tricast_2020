package com.tricast.repositories.entities.enums;

public enum OffDayType {
	PAID(1), SICK(2), OTHER(3);
	
private long typeId;
		
	private OffDayType(long typeId) {
	this.typeId = typeId;
}

	public long getTypeId() {
		return typeId;
	}

	public static OffDayType getType(long id)
	{
		return id == 1 ? PAID : SICK ;
	}
}
