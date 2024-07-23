package com.lib.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_imagefiles")
public class ImageFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ImageData imageData;


    public ImageFile(String name, String type, ImageData imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}
