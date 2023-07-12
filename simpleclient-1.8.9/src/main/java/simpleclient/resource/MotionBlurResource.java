package simpleclient.resource;

import net.minecraft.client.resource.ResourceMetadataProvider;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import simpleclient.feature.Motionblur;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

public class MotionBlurResource implements Resource {
    private static String JSON = "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"motionblur\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"strength\",\"values\":[%.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}";

    @Override
    public Identifier getId() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return IOUtils.toInputStream(String.format(Locale.ENGLISH, JSON, Motionblur.STRENGTH), Charset.defaultCharset());
    }

    @Override
    public boolean hasMetadata() {
        return false;
    }

    @Override
    public <T extends ResourceMetadataProvider> T getMetadata(String string) {
        return null;
    }

    @Override
    public String getResourcePackName() {
        return null;
    }
}