package com.gmenegatto.wallet_api.domain.wallet;

import com.gmenegatto.wallet_api.domain.base.BaseEntity;
import com.gmenegatto.wallet_api.domain.user.User;
import jakarta.persistence.*;

@Table(name = "wlt_wallets")
@Entity
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usr_user_id", nullable = false)
    private User user;

    public Wallet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
