package com.inamona;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class KonokiConfiguration extends Configuration {
    @Getter
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
}
