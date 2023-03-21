import { Component } from '@angular/core';
import Feature from 'ol/Feature';
import { MapService } from 'src/app/services/map.service';
import { DrawnFeature } from './drawn-feature';

@Component({
  selector: 'app-draw',
  templateUrl: './draw.component.html',
  styleUrls: ['./draw.component.css']
})
export class DrawComponent {

  public features: DrawnFeature[] = [];

  displayModal: boolean = false;

  currentFeatureName!: string | null;

  private currentFeature!: Feature;

  constructor(private mapService: MapService) { }

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

  public newDraw() {
    this.mapService.activateDraw();
  }

  public deleteFeature(drawnFeature: DrawnFeature) {
    this.mapService.deleteDrawnFeature(drawnFeature.feature);
    const index = this.features.indexOf(drawnFeature, 0);
    if (index > -1) {
      this.features.splice(index, 1);
    }
  }

  validateDrawing() {
    this.features.push(new DrawnFeature(this.currentFeatureName ?? (new Date()).getTime().toString(), this.currentFeature));
    this.closeModal(false);
  }

  cancelDrawing() {
    this.closeModal(true);
  }

  public closeModal(deleteCurrentFeature: boolean) {
    if (deleteCurrentFeature && this.currentFeature != null) {
      this.mapService.deleteDrawnFeature(this.currentFeature);
    }
    this.displayModal = false;
    this.currentFeatureName = null;
    this.currentFeature = null as any;
  }
}
