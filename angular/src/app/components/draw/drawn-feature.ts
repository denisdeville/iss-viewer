import Feature from "ol/Feature";

export class DrawnFeature {
    private _name: string;
    private _feature: Feature;

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
}