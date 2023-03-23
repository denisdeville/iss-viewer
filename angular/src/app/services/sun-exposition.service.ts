import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SunExposureDto } from '../models/sun-exposures-dto';

@Injectable({
  providedIn: 'root'
})
export class SunExpositionService {

  private baseUrl = 'http://localhost:8080/iss/sun'

  constructor(private httpClient: HttpClient) { }

  public getSatelliteSunExposures(): Observable<SunExposureDto[]> {
    return this.httpClient.get<SunExposureDto[]>(`${this.baseUrl}`);
  }
}
