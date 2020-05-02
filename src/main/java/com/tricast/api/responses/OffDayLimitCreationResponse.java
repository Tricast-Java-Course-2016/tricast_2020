package com.tricast.api.responses;

import java.math.BigDecimal;

import com.tricast.repositories.entities.enums.OffDayType;

public class OffDayLimitCreationResponse {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private BigDecimal maximumAmount;
	
	private OffDayType type;
	
	private Integer userId;
	
	private String year;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(BigDecimal maximumAmount) {
		this.maximumAmount = maximumAmount;
	}

	public OffDayType getType() {
		return type;
	}

	public void setType(OffDayType type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OffDayLimitCreationResponse other = (OffDayLimitCreationResponse) obj;
        if (maximumAmount == null) {
            if (other.maximumAmount != null) {
                return false;
            }
        } else if (!maximumAmount.equals(other.maximumAmount)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        if (year == null) {
            if (other.year != null) {
                return false;
            }
        } else if (!year.equals(other.year)) {
            return false;
        }
        return true;
	}
}
