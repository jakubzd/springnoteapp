package com.example.springproject.note;

import com.example.springproject.category.Category;
import com.example.springproject.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTES")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "body")
    private String body;

    //CascadeType.ALL deletes related in this case Category from database.
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    private LocalDateTime dateOfPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getCategoryName() {
        return this.category.getName();
    }
}
