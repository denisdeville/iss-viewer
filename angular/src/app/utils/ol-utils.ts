import Fill from "ol/style/Fill";
import Circle from "ol/style/Circle";
import Icon from "ol/style/Icon";
import Stroke from "ol/style/Stroke";
import Style from "ol/style/Style";
import Text from "ol/style/Text";

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

    public static getLineStringStartEndStyle(textToDisplay: string): Style {
        return new Style({
            image: new Circle({
                radius: 5,
                fill: new Fill({
                    color: 'blue',
                }),
                stroke: new Stroke({
                    color: 'white',
                    width: 2,
                })
            }),
            stroke: new Stroke({
                color: 'white',
                width: 2,
            }),
            text: new Text({
                text: textToDisplay,
                font: 'bold 15px Calibri,sans-serif',
                fill: new Fill({
                    color: 'black',
                }),
                offsetY: -20,
                stroke: new Stroke({
                    color: 'white',
                    width: 2,
                }),
            }),
          });
    }
}