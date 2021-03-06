package io.zonky.test.db.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.resolver.MigrationResolver;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a snapshot of configuration parameters of a flyway instance.
 * It is necessary because of mutability of flyway instances.
 */
public class FlywayConfigSnapshot {

    private static final int flywayVersion = FlywayClassUtils.getFlywayVersion();

    // not included in equals and hashCode methods
    private final ClassLoader classLoader;
    private final DataSource dataSource;
    private final FlywayCallback[] callbacks; // the callbacks are modified during the migration

    // included in equals and hashCode methods
    // but it will work only for empty arrays (that is common use-case)
    // because of missing equals and hashCode methods
    // on classes implementing these interfaces,
    // note the properties require special treatment suitable for arrays
    private final MigrationResolver[] resolvers;

    // included in equals and hashCode methods
    // but these properties require special treatment suitable for arrays
    private final String[] locations;
    private final String[] schemas;

    // included in equals and hashCode methods
    private final MigrationVersion baselineVersion;
    private final MigrationVersion target;
    private final Map<String, String> placeholders;
    private final String table;
    private final String baselineDescription;
    private final String repeatableSqlMigrationPrefix;
    private final String sqlMigrationSeparator;
    private final String sqlMigrationPrefix;
    private final String sqlMigrationSuffix;
    private final String placeholderPrefix;
    private final String placeholderSuffix;
    private final String encoding;
    private final boolean skipDefaultResolvers;
    private final boolean skipDefaultCallbacks;
    private final boolean placeholderReplacement;
    private final boolean baselineOnMigrate;
    private final boolean outOfOrder;
    private final boolean ignoreMissingMigrations;
    private final boolean ignoreFutureMigrations;
    private final boolean validateOnMigrate;
    private final boolean cleanOnValidationError;
    private final boolean cleanDisabled;
    private final boolean allowMixedMigrations;
    private final boolean mixed;
    private final boolean group;
    private final String installedBy;

    public FlywayConfigSnapshot(Flyway flyway) {
        this.classLoader = flyway.getClassLoader();
        this.dataSource = flyway.getDataSource();
        this.resolvers = flyway.getResolvers();
        this.callbacks = flyway.getCallbacks();
        this.sqlMigrationSuffix = flyway.getSqlMigrationSuffix();
        this.sqlMigrationSeparator = flyway.getSqlMigrationSeparator();
        this.sqlMigrationPrefix = flyway.getSqlMigrationPrefix();
        this.placeholderSuffix = flyway.getPlaceholderSuffix();
        this.placeholderPrefix = flyway.getPlaceholderPrefix();
        this.placeholders = flyway.getPlaceholders();
        this.target = flyway.getTarget();
        this.table = flyway.getTable();
        this.schemas = flyway.getSchemas();
        this.encoding = flyway.getEncoding();
        this.locations = flyway.getLocations();
        this.outOfOrder = flyway.isOutOfOrder();
        this.validateOnMigrate = flyway.isValidateOnMigrate();
        this.cleanOnValidationError = flyway.isCleanOnValidationError();

        if (flywayVersion >= 31) {
            this.baselineVersion = flyway.getBaselineVersion();
            this.baselineDescription = flyway.getBaselineDescription();
            this.baselineOnMigrate = flyway.isBaselineOnMigrate();
        } else {
            this.baselineVersion = null;
            this.baselineDescription = null;
            this.baselineOnMigrate = false;
        }

        if (flywayVersion >= 32) {
            this.placeholderReplacement = flyway.isPlaceholderReplacement();
        } else {
            this.placeholderReplacement = true;
        }

        if (flywayVersion >= 40) {
            this.skipDefaultResolvers = flyway.isSkipDefaultResolvers();
            this.skipDefaultCallbacks = flyway.isSkipDefaultCallbacks();
            this.repeatableSqlMigrationPrefix = flyway.getRepeatableSqlMigrationPrefix();
            this.ignoreFutureMigrations = flyway.isIgnoreFutureMigrations();
            this.cleanDisabled = flyway.isCleanDisabled();
        } else {
            this.skipDefaultResolvers = false;
            this.skipDefaultCallbacks = false;
            this.repeatableSqlMigrationPrefix = "R";
            this.ignoreFutureMigrations = true;
            this.cleanDisabled = false;
        }

        if (flywayVersion >= 41) {
            this.ignoreMissingMigrations = flyway.isIgnoreMissingMigrations();
            this.allowMixedMigrations = flyway.isAllowMixedMigrations();
            this.installedBy = flyway.getInstalledBy();
        } else {
            this.ignoreMissingMigrations = false;
            this.allowMixedMigrations = false;
            this.installedBy = null;
        }

        if (flywayVersion >= 42) {
            this.mixed = flyway.isMixed();
            this.group = flyway.isGroup();
        } else {
            this.mixed = false;
            this.group = false;
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public MigrationVersion getBaselineVersion() {
        return baselineVersion;
    }

    public String getBaselineDescription() {
        return baselineDescription;
    }

    public MigrationResolver[] getResolvers() {
        return resolvers;
    }

    public boolean isSkipDefaultResolvers() {
        return skipDefaultResolvers;
    }

    public FlywayCallback[] getCallbacks() {
        return callbacks;
    }

    public boolean isSkipDefaultCallbacks() {
        return skipDefaultCallbacks;
    }

    public String getSqlMigrationSuffix() {
        return sqlMigrationSuffix;
    }

    public String getRepeatableSqlMigrationPrefix() {
        return repeatableSqlMigrationPrefix;
    }

    public String getSqlMigrationSeparator() {
        return sqlMigrationSeparator;
    }

    public String getSqlMigrationPrefix() {
        return sqlMigrationPrefix;
    }

    public boolean isPlaceholderReplacement() {
        return placeholderReplacement;
    }

    public String getPlaceholderSuffix() {
        return placeholderSuffix;
    }

    public String getPlaceholderPrefix() {
        return placeholderPrefix;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public MigrationVersion getTarget() {
        return target;
    }

    public String getTable() {
        return table;
    }

    public String[] getSchemas() {
        return schemas;
    }

    public String getEncoding() {
        return encoding;
    }

    public String[] getLocations() {
        return locations;
    }

    public boolean isBaselineOnMigrate() {
        return baselineOnMigrate;
    }

    public boolean isOutOfOrder() {
        return outOfOrder;
    }

    public boolean isIgnoreMissingMigrations() {
        return ignoreMissingMigrations;
    }

    public boolean isIgnoreFutureMigrations() {
        return ignoreFutureMigrations;
    }

    public boolean isValidateOnMigrate() {
        return validateOnMigrate;
    }

    public boolean isCleanOnValidationError() {
        return cleanOnValidationError;
    }

    public boolean isCleanDisabled() {
        return cleanDisabled;
    }

    public boolean isAllowMixedMigrations() {
        return allowMixedMigrations;
    }

    public boolean isMixed() {
        return mixed;
    }

    public boolean isGroup() {
        return group;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlywayConfigSnapshot that = (FlywayConfigSnapshot) o;
        return skipDefaultResolvers == that.skipDefaultResolvers &&
                skipDefaultCallbacks == that.skipDefaultCallbacks &&
                placeholderReplacement == that.placeholderReplacement &&
                baselineOnMigrate == that.baselineOnMigrate &&
                outOfOrder == that.outOfOrder &&
                ignoreMissingMigrations == that.ignoreMissingMigrations &&
                ignoreFutureMigrations == that.ignoreFutureMigrations &&
                validateOnMigrate == that.validateOnMigrate &&
                cleanOnValidationError == that.cleanOnValidationError &&
                cleanDisabled == that.cleanDisabled &&
                allowMixedMigrations == that.allowMixedMigrations &&
                mixed == that.mixed &&
                group == that.group &&
                Arrays.equals(resolvers, that.resolvers) &&
                Arrays.equals(locations, that.locations) &&
                Arrays.equals(schemas, that.schemas) &&
                Objects.equals(baselineVersion, that.baselineVersion) &&
                Objects.equals(target, that.target) &&
                Objects.equals(placeholders, that.placeholders) &&
                Objects.equals(table, that.table) &&
                Objects.equals(baselineDescription, that.baselineDescription) &&
                Objects.equals(repeatableSqlMigrationPrefix, that.repeatableSqlMigrationPrefix) &&
                Objects.equals(sqlMigrationSeparator, that.sqlMigrationSeparator) &&
                Objects.equals(sqlMigrationPrefix, that.sqlMigrationPrefix) &&
                Objects.equals(sqlMigrationSuffix, that.sqlMigrationSuffix) &&
                Objects.equals(placeholderPrefix, that.placeholderPrefix) &&
                Objects.equals(placeholderSuffix, that.placeholderSuffix) &&
                Objects.equals(encoding, that.encoding) &&
                Objects.equals(installedBy, that.installedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                Arrays.hashCode(resolvers), Arrays.hashCode(locations), Arrays.hashCode(schemas),
                baselineVersion, target, placeholders, table, baselineDescription,
                repeatableSqlMigrationPrefix, sqlMigrationSeparator, sqlMigrationPrefix,
                sqlMigrationSuffix, placeholderPrefix, placeholderSuffix, encoding,
                skipDefaultResolvers, skipDefaultCallbacks, placeholderReplacement,
                baselineOnMigrate, outOfOrder, ignoreMissingMigrations, ignoreFutureMigrations,
                validateOnMigrate, cleanOnValidationError, cleanDisabled,
                allowMixedMigrations, mixed, group, installedBy);
    }
}
