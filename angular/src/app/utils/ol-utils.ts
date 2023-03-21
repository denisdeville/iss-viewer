import Icon from "ol/style/Icon";
import Style from "ol/style/Style";

export class OlUtils {
    public static getDefaultIconStyle(): Style {
        return new Style({
            image: new Icon({
              anchor: [0.5, 0.5],
              scale: 0.5,
              src: 'assets/iss.png',
            }),
        })
    }

    public static getSunIconStyle(): Style {
        return new Style({
            image: new Icon({
              anchor: [0.5, 0.5],
              scale: 0.5,
              src: 'assets/iss_marker_sun.png',
            }),
        })
    }
}