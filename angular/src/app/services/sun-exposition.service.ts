import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SunExposureDto } from '../models/sun-exposures-dto';
import { SunExposurePaginationDTO } from '../models/sun-exposures-pagination-dto';

@Injectable({
  providedIn: 'root'
})
export class SunExpositionService {

  private baseUrl = `${environment.apiUrl}`;

  constructor(private httpClient: HttpClient) { }

  public getSatelliteSunExposures(): Observable<SunExposureDto[]> {
    return this.httpClient.get<SunExposureDto[]>(`${this.baseUrl}/iss/sun`);
  }

  public getSatelliteSunExposuresPagination(pageNumber: number, pageSize: number): Observable<SunExposurePaginationDTO> {
    return this.httpClient.get<SunExposurePaginationDTO>(`${this.baseUrl}/iss/sun/pagination?pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }
}
