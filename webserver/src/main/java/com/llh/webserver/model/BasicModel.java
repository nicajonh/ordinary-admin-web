package com.llh.webserver.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BasicModel
 * 单表模型类的基础父类。
 * <p>
 * CreatedAt: 2020-04-18 12:01
 *
 * @author llh
 */
@MappedSuperclass
@Data
public class BasicModel implements Serializable {
    private static final long serialVersionUID = 7777L;
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 64)
    private String id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Version
    private Integer version;

    private Integer dataStatus;


    @Column(length = 64)
    private String updatedBy;
    @Column(length = 64)
    private String createdBy;
}
