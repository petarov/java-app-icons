package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.entities.UrlIcon;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IconParser {

    List<UrlIcon> parse(InputStream input);

}
