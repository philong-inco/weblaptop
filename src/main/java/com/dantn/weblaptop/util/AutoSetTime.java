package com.dantn.weblaptop.util;


import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Calendar;

public class AutoSetTime {

    @PrePersist
    public void onCreate(BaseEntity entity) {
        entity.setCreateAt(getCurrentTimes());
        entity.setModifyAt(getCurrentTimes());
    }

    @PreUpdate
    public void onUpdate(BaseEntity entity){
        entity.setModifyAt(getCurrentTimes());
    }

    public Long getCurrentTimes(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
