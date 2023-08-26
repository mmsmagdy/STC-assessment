package com.java.stcassessment.model;

import com.java.stcassessment.enums.ItemType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Item parent;

    @OneToOne(mappedBy = "item")
    private File file;

    @ManyToOne
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;

    public Item(ItemType type, String name, PermissionGroup permissionGroup) {
        this.type = type;
        this.name = name;
        this.permissionGroup = permissionGroup;
    }

}
