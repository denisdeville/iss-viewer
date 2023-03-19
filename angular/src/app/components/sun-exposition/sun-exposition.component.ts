import { Component } from '@angular/core';
import { SatelliteInfos } from 'src/app/models/satellite-infos';
import { SunExpositionService } from 'src/app/services/sun-exposition.service';
import { MapService } from '../../services/map.service';

@Component({
  selector: 'app-sun-exposition',
  templateUrl: './sun-exposition.component.html',
  styleUrls: ['./sun-exposition.component.css']
})
export class SunExpositionComponent {

  private timestamps = [
    1679253883,
    1679253823,
    1679253763,
    1679253703,
    1679253643,
    1679253583,
  ]

  expositions: SatelliteInfos[] = [];

  constructor(private mapService: MapService, private sunExpositionService: SunExpositionService){}

  ngOnInit() {
    this.sunExpositionService.getSatelliteSunExpositionsForTimestamp(25544, this.timestamps)
      .subscribe(res => this.expositions = res);
  }

}
