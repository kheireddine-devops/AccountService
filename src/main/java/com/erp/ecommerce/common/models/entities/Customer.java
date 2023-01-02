package com.erp.ecommerce.common.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @ToString(callSuper = true)
public class Customer extends User implements Serializable {
}