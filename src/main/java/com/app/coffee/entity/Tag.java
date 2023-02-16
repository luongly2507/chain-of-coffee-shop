package com.app.coffee.entity;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.app.coffee.audit.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
    
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name="tag")
@Table(uniqueConstraints = @UniqueConstraint(columnNames= {"branch_id","name"}))
public class Tag  extends Auditable<String>{
    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "branch_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Branch branch;

    private String name;

    private String status;
    
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Order> orders;
}
