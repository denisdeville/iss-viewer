import Icon from "ol/style/Icon";
import Style from "ol/style/Style";

export class OlUtils {
    public static getDefaultIconStyle(): Style {
        return new Style({
            image: new Icon({
              anchor: [0.5, 0.5],
              scale: 0.1,
              src: 'assets/iss_marker.png',
            }),
        })
    }
}