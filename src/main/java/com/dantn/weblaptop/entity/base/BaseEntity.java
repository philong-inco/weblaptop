package com.dantn.weblaptop.entity.base;

import com.dantn.weblaptop.util.AutoSetTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AutoSetTime.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "create_at", updatable = false, insertable = false)
    private Long createAt;

    @Column(name = "modify_at")
    private Long modifyAt;

    @Column(name = "create_at")
    private String createBy;

    @Column(name = "modify_by")
    private Long modifyBy;
}
