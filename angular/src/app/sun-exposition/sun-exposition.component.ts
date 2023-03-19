import { Component } from '@angular/core';
import { MapService } from '../services/map.service';

@Component({
  selector: 'app-sun-exposition',
  templateUrl: './sun-exposition.component.html',
  styleUrls: ['./sun-exposition.component.css']
})
export class SunExpositionComponent {
  constructor(private mapService: MapService){}
}
