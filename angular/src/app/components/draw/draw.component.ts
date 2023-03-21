import { Component } from '@angular/core';
import Feature from 'ol/Feature';
import { DrawnFeaturesService } from 'src/app/services/drawn-features.service';
import { MapService } from 'src/app/services/map.service';
import { AlertFeature } from './drawn-feature';

@Component({
  selector: 'app-draw',
  templateUrl: './draw.component.html',
  styleUrls: ['./draw.component.css']
})
export class DrawComponent {

  displayModal: boolean = false;

  currentAlertName!: string | null;

  private currentFeature!: Feature;

  constructor(private mapService: MapService, public drawnFeaturesService: DrawnFeaturesService) { }

  ngOnInit() {
    this.mapService.onDrawEnd$.subscribe(feature => {
      this.currentFeature = feature;
      this.displayModal = true;
      this.mapService.deactivateDraw();
    })
  }

  showModalDialog() {
    this.displayModal = true;
  }

  public newAlert() {
    this.mapService.activateDraw();
  }

  public deleteAlert(drawnFeature: AlertFeature) {
    this.mapService.deleteDrawnFeature(drawnFeature.feature);
    this.drawnFeaturesService.removeFeature(drawnFeature);
  }
  
  public zoomToAlert(drawnFeature: AlertFeature) {
    this.mapService.zoomTofeature(drawnFeature.feature);
  }

  validateAlert() {
    this.drawnFeaturesService.addFeature(new AlertFeature(this.currentAlertName ?? (new Date()).getTime().toString(), this.currentFeature));
    this.closeModal(false);
  }

  cancelAlert() {
    this.closeModal(true);
  }

  public closeModal(deleteCurrentFeature: boolean) {
    if (deleteCurrentFeature && this.currentFeature != null) {
      this.mapService.deleteDrawnFeature(this.currentFeature);
    }
    this.displayModal = false;
    this.currentAlertName = null;
    this.currentFeature = null as any;
  }
}
