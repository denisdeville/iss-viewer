import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SatelliteInfos } from '../models/satellite-infos';

@Injectable({
  providedIn: 'root'
})
export class SatellitePositionService {

  private baseUrl = 'http://localhost:8080/iss/position'

  constructor(private httpClient: HttpClient) { }

  public getSatelliteInfos(id: number): Observable<SatelliteInfos> {
    return this.httpClient.get<SatelliteInfos>(`${this.baseUrl}/${id}`);
  }
}
