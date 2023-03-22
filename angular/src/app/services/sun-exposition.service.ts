import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SatelliteInfos } from '../models/satellite-infos';

@Injectable({
  providedIn: 'root'
})
export class SunExpositionService {

  private baseUrl = 'http://ec2-3-66-89-46.eu-central-1.compute.amazonaws.com:8080/iss/sun'

  constructor(private httpClient: HttpClient) { }

  public getSatelliteSunExpositionsForTimestamp(id: number, timestamps: number[]): Observable<SatelliteInfos[]> {
    return this.httpClient.get<SatelliteInfos[]>(`${this.baseUrl}/${id}?timestamps=${timestamps.join(',')}`);
  }
}
