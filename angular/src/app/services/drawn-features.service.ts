import { Injectable } from '@angular/core';
import Point from 'ol/geom/Point';
import { AlertFeature } from '../components/draw/drawn-feature';
import { CustomMessagesService } from './custom-messages.service';

@Injectable({
    providedIn: 'root'
})
export class DrawnFeaturesService {

    private _features: AlertFeature[] = [];


    constructor(private customMessageService: CustomMessagesService) { }

    public addFeature(newFature: AlertFeature) {
        this._features.push(newFature);
    }

    public removeFeature(featureToRemove: AlertFeature) {
        const index = this.features.indexOf(featureToRemove, 0);
        if (index > -1) {
            this.features.splice(index, 1);
        }
    }

    public get features(): AlertFeature[] {
        return this._features;
    }

    public checkIntersections(point: Point) {
        this._features.forEach((feature: AlertFeature) => {
            const featureGeometry = feature.feature.getGeometry();
            if (featureGeometry) {
                if (featureGeometry.intersectsCoordinate(point.getCoordinates())) {
                    if (!feature.notificationPaused) {
                        this.customMessageService.addAlert('Zone alert', `The ISS is now in your alert zone "${feature.name}"`, feature.feature);
                        feature.pauseNotification();
                    }
                }
            }
        })
    }
}
