import Feature from "ol/Feature";

export class AlertFeature {
    private _name: string;
    private _feature: Feature;
    private _notificationPaused = false;

    constructor(name: string, feature: Feature) {
        this._name = name;
        this._feature = feature;
    }

    public get name(): string {
        return this._name;
    }

    public get feature(): Feature {
        return this._feature;
    }

    public get notificationPaused(): boolean {
        return this._notificationPaused;
    }

    public pauseNotification() {
        this._notificationPaused = true;
        setTimeout(() => {
            this._notificationPaused = false;
        }, 10000);
    }
}