import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SunExposureDto } from '../models/sun-exposures-dto';

@Injectable({
  providedIn: 'root'
})
export class SunExpositionService {

  private baseUrl = `${environment.apiUrl}/iss/sun`;

  constructor(private httpClient: HttpClient) { }

  public getSatelliteSunExposures(): Observable<SunExposureDto[]> {
    return this.httpClient.get<SunExposureDto[]>(`${this.baseUrl}`);
  }
}
