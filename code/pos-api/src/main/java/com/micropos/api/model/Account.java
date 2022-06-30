package com.micropos.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Document(collection = "Users")
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    /**
     * same as accountId
     */
    @Id
    @JsonProperty("accountId")
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

