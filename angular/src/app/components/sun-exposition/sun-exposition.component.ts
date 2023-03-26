import { Component } from '@angular/core';
import { SunExposureDto } from 'src/app/models/sun-exposures-dto';
import { CustomMessagesService } from 'src/app/services/custom-messages.service';
import { SunExpositionService } from 'src/app/services/sun-exposition.service';
import { MapService } from '../../services/map.service';

@Component({
  selector: 'app-sun-exposition',
  templateUrl: './sun-exposition.component.html',
  styleUrls: ['./sun-exposition.component.css']
})
export class SunExpositionComponent {

  loading = true;

  private _sunExposures: SunExposureDto[] = [];

  currentPage = 0;

  totalPage = 0;

  pageSize = 10;

  pageCount = 0;

  private cachedSunExposure = new Map<number, SunExposureDto[]>();

  constructor(private mapService: MapService, 
    private customMessageService: CustomMessagesService,
    private sunExpositionService: SunExpositionService) { }

  ngOnInit() {
    this.loadNextPage();
  }

  public onRowSelected(event: any) {
    this.zoomToExpo(event.data);
  }

  public onRowUnselect() {
    this.mapService.clearSunExposuresLayer();
  }


  public loadNextPage(): void {
    this.loading = true;
    const cachedData = this.cachedSunExposure.get(this.currentPage);
    if (cachedData != null) {
      this.sunExposures = cachedData;
      this.loading = false;
    } else {
      this.sunExpositionService.getSatelliteSunExposuresPagination(this.currentPage, this.pageSize)
        .subscribe({
          next: (res) => {
            this.loading = false;
            this.pageCount = res.pageCount;
            this.sunExposures = res.sunExposures;
            this.cachedSunExposure.set(this.currentPage, res.sunExposures);
          },
          error: error => this.customMessageService.handleError(error)
        });
    }
  }

  zoomToExpo(sunExposition: SunExposureDto) {
    this.mapService.highlightSunExposition(sunExposition);
  }

  next() {
    this.currentPage++;
    this.loadNextPage();
  }

  prev() {
    this.currentPage--;
    this.loadNextPage();
  }

  reset() {
    this.currentPage = 0;
    this.loadNextPage();
  }

  isLastPage(): boolean {
    return this.currentPage == this.pageCount-1;
  }

  isFirstPage(): boolean {
    return this.currentPage == 0;
  }

  public get sunExposures(): SunExposureDto[] {
    return this._sunExposures;
  }

  private set sunExposures(value: SunExposureDto[]) {
    this._sunExposures = value;
  }
}
