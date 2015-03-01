package org.zapodot.jackson.java8;

import com.fasterxml.jackson.core.Version;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author zapodot
 */
public class PackageVersionTest {

    @Test
    public void testVersion() throws Exception {
        final Version version = new PackageVersion().version();
        assertNotNull(version);
        assertNotNull(version.getArtifactId());
        assertNotNull(version.getGroupId());

    }
}
