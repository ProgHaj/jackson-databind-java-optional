package org.zapodot.jackson.java8;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

/**
 * <p>Module that installs Java 8 optional support into Jackson.</p>
 *
 * <p>This is very closely modeled after the support in jackson-datatype-guava for Optionals</p>
 *
 * @author R.J. Lorimer [rj@realjenius.com]
 */
public class JavaOptionalModule extends Module {

    @Override
    public String getModuleName() {
        return "java8";
    }

    @Override
    public Version version() {
        return new PackageVersion().version();
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addDeserializers(new JavaOptionalDeserializers());
        context.addSerializers(new JavaOptionalSerializers());
        context.addBeanSerializerModifier(new JavaOptionalBeanSerializerModifier());
    }

}
